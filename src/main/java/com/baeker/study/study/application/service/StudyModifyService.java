package com.baeker.study.study.application.service;

import com.baeker.study.global.exception.service.NoPermissionException;
import com.baeker.study.global.exception.service.NotFoundException;
import com.baeker.study.study.adapter.in.reqDto.StudyModifyReqDto;
import com.baeker.study.study.application.port.in.SnapshotUseCase;
import com.baeker.study.study.application.port.in.StudyModifyUseCase;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.BaekjoonDto;
import com.baeker.study.study.in.reqDto.UpdateLeaderReqDto;
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
    private final SnapshotUseCase snapshotUseCase;
    private final ApplicationEventPublisher publisher;

    @Override
    public UpdateResDto info(Study study, Long memberId, StudyModifyReqDto dto) {
        isLeader(study, memberId);

        Study modified = study.modifyStudy(
                dto.getName(), dto.getAbout(), dto.getCapacity()
        );
        repository.save(modified);
        return new UpdateResDto(modified.getId());
    }

    @Override
    public StudyResDto leader(Study study, Long memberId, UpdateLeaderReqDto dto) {
        isLeader(study, memberId);
        isMember(study.getId(), dto.getNewLeader());

        Study modified = repository.save(
                study.modifyLeader(dto.getNewLeader())
        );
        return new StudyResDto(modified);
    }

    private static void isLeader(Study study, Long memberId) {
        if (study.getLeader() != memberId)
            throw new NoPermissionException("권한이 없습니다.");
    }

    private void isMember(Long studyId, Long memberId) {
        List<StudyResDto> dtos = queryUseCase.byMemberId(memberId, 1);

        for (StudyResDto dto : dtos)
            if (dto.getId() == studyId) return;

        throw new NotFoundException("가입된 회원이 아닙니다.");
    }

    @Override
    public UpdateResDto xp(Study study, double addXp) {
        study.xpUp(addXp);
        return new UpdateResDto(study.getId());
    }

    @Override
    public void solvedCount(List<StudyResDto> studyList, SolvedCountReqDto dto) {
        if (studyList.size() == 0) return;
        updateSolvedCount(studyList, dto);
    }

    @Override
    public void ranking(List<Study> studyList) {
        for (int i = 0; i < studyList.size(); i++)
            studyList.get(i).updateRanking(i + 1);
    }


    private void updateSolvedCount(List<StudyResDto> studyList, SolvedCountReqDto dto) {
        for (StudyResDto studyDto : studyList) {
            Study study = queryUseCase.byId(studyDto.getId());
            Study saveStudy = repository.save(
                    study.updateSolvedCount(dto)
            );

            BaekjoonDto reqDto = new BaekjoonDto(dto);
            snapshotUseCase.updateSnapshot(saveStudy, reqDto, 0);
        }
    }
}
