package com.baeker.study.study.application.service;

import com.baeker.study.global.exception.NotFoundException;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.global.feign.dto.CandidateResDto;
import com.baeker.study.global.feign.dto.MembersReqDto;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.application.port.out.persistence.StudyQueryRepositoryPort;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.in.resDto.MemberResDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.baeker.study.myStudy.domain.entity.StudyStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyQueryService implements StudyQueryUseCase {

    private final StudyRepositoryPort repository;
    private final StudyQueryRepositoryPort queryRepository;
    private final MemberClient memberClient;

    @Override
    public Study byId(Long id) {
        Optional<Study> byId = repository.findById(id);

        if (byId.isPresent())
            return byId.get();

        throw new NotFoundException("존재하지 않는 id");
    }

    @Override
    public Study byName(String name) {
        Optional<Study> byName = repository.findByName(name);

        if (byName.isPresent())
            return byName.get();

        throw new NotFoundException("존재하지 않는 name");
    }

    @Override
    public List<StudyResDto> all() {
        List<Study> all = repository.findAll();

        if (all().size() == 0)
            throw new NotFoundException("Study가 존재하지 않습니다.");

        return toStudyDtoList(all);
    }

    private List<StudyResDto> toStudyDtoList(List<Study> all) {
        ArrayList<StudyResDto> resDtos = new ArrayList<>();

        for (Study study : all)
            resDtos.add(new StudyResDto(study));

        return resDtos;
    }

    @Override
    public List<StudyResDto> byMemberId(Long memberId, int status) {
        StudyStatus studyStatus;
        switch (status) {
            case 1 -> studyStatus = MEMBER;
            case 2 -> studyStatus = PENDING;
            default -> studyStatus = INVITING;
        }
        return queryRepository.byMemberId(memberId, studyStatus);
    }

    @Override
    public List<MemberResDto> byMemberList(Study study) {
        List<Long> memberIdList = queryRepository.byMemberList(study, MEMBER);

        return memberClient.findMemberList(
                new MembersReqDto(memberIdList, "MEMBER")
        ).getData();
    }

    @Override
    public CandidateResDto byCandidateList(Study study) {
        List<Long> pending = queryRepository.byMemberList(study, PENDING);
        List<Long> inviting = queryRepository.byMemberList(study, INVITING);

        List<MemberResDto> pendingDto = memberClient.findMemberList(new MembersReqDto(pending, "PENDING")).getData();
        List<MemberResDto> invitingDto = memberClient.findMemberList(new MembersReqDto(inviting, "INVITING")).getData();

        return new CandidateResDto(pendingDto, invitingDto);
    }

    @Override
    public List<StudyResDto> allOrderByRanking(int page, int content) {
        return queryRepository.allOrderByRanking(page, content);
    }

    @Override
    public List<StudyResDto> byInput(String input, int page, int content) {
        return queryRepository.byInput(input, page, content);
    }

    @Override
    public List<StudySnapshot> snapshotByStudy(Study study) {
        return null;
    }
}
