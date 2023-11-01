package com.baeker.study.myStudy.application.service;

import com.baeker.study.global.exception.service.NotFoundException;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.myStudy.application.port.in.MyStudyQueryUseCase;
import com.baeker.study.myStudy.application.port.out.persistence.MyStudyQueryRepositoryPort;
import com.baeker.study.myStudy.application.port.out.persistence.MyStudyRepositoryPort;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.legacy.in.resDto.MyStudyResDto;
import com.baeker.study.study.domain.entity.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyStudyQueryService implements MyStudyQueryUseCase {

    private final MyStudyRepositoryPort repository;
    private final MyStudyQueryRepositoryPort queryRepository;
    private final MemberClient memberClient;

    @Override
    public MyStudy byId(Long myStudyId) {
        Optional<MyStudy> byId = repository.findById(myStudyId);

        if (byId.isPresent())
            return byId.get();

        throw new NotFoundException("존재하지 않는 id");
    }

    @Override
    public MyStudy byStudyIdAndMemberId(Long memberId, Study study) {
        MyStudy myStudy = queryRepository.byStudyIdAndMemberId(memberId, study);

        if (myStudy == null)
            throw new NotFoundException("가입하지 않은 study");

        return myStudy;
    }

    @Override
    public MyStudyResDto toDtoByStudyIdAndMemberId(Long memberId, Study study) {
        Integer memberRanking = memberClient.findRanking(memberId);
        MyStudy myStudy = byStudyIdAndMemberId(memberId, study);
        return new MyStudyResDto(myStudy, memberRanking, study.getRanking());
    }

    @Override
    public void isMember(Long studyId, Long memberId) {
        List<MyStudy> myStudies = queryRepository.byMemberId(studyId, memberId);

        if (myStudies.size() == 0)
            throw new NotFoundException("스터디에 존재하지 않는 회원");
    }
}