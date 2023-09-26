package com.baeker.study.study.application.service;

import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.global.exception.NotFoundException;
import com.baeker.study.study.adapter.in.reqDto.StudyModifyResDto;
import com.baeker.study.study.application.port.in.StudyModifyUseCase;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.event.AddSolvedCountEvent;
import com.baeker.study.study.in.reqDto.AddXpReqDto;
import com.baeker.study.study.in.reqDto.UpdateLeaderReqDto;
import com.baeker.study.study.in.reqDto.UpdateReqDto;
import com.baeker.study.study.in.resDto.SolvedCountReqDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.baeker.study.study.in.resDto.UpdateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyModifyService implements StudyModifyUseCase {

    private final StudyRepositoryPort repository;
    private final StudyQueryUseCase queryUseCase;
    private final ApplicationEventPublisher publisher;

    @Override
    public UpdateResDto info(Study study, Long memberId, StudyModifyResDto dto) {
        if (study.getLeader() != memberId)
            throw new NoPermissionException("권한이 없습니다.");

        Study modified = study.modifyStudy(
                dto.getName(), dto.getAbout(), dto.getCapacity()
        );

        repository.save(modified);
        return new UpdateResDto(modified.getId());
    }

    @Override
    public StudyResDto leader(Study study, Long memberId, UpdateLeaderReqDto dto) {
        if (study.getLeader() != memberId)
            throw new NoPermissionException("스터디 리더만 위임이 가능합니다.");

        if (isMember(study.getId(), memberId))
            throw new NotFoundException("존재하지 않는 회원입니다.");

        Study modified = repository.save(
                study.modifyLeader(memberId)
        );
        return new StudyResDto(modified);
    }

    private boolean isMember(Long studyId, Long memberId) {
        List<StudyResDto> dtos = queryUseCase.byMemberId(memberId, 1);

        for (StudyResDto dto : dtos)
            if (dto.getId() == studyId)
                return true;
        return false;
    }

    @Override
    public UpdateResDto xp(Study study, AddXpReqDto dto) {
        study.xpUp(dto.getXp());
        return new UpdateResDto(study.getId());
    }

    @Override
    public void solvedCount(SolvedCountReqDto dto) {
        publisher.publishEvent(new AddSolvedCountEvent(this, dto));
    }

    @Override
    public void ranking(List<Study> studyList) {
        for (int i = 0; i < studyList.size(); i++)
            studyList.get(i).updateRanking(i + 1);
    }
}
