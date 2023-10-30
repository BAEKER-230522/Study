package com.baeker.study.study.application.service;

import com.baeker.study.global.exception.service.NoPermissionException;
import com.baeker.study.myStudy.application.port.in.MyStudyDeleteUseCase;
import com.baeker.study.study.application.port.in.SnapshotUseCase;
import com.baeker.study.study.application.port.in.StudyDeleteUseCase;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyDeleteService implements StudyDeleteUseCase {

    private final StudyRepositoryPort studyRepository;
    private final MyStudyDeleteUseCase myStudyDeleteUseCase;
    private final SnapshotUseCase snapshotUseCase;

    @Override
    public void study(Study study, Long memberId) {
        isLeader(study.getLeader(), memberId);

        deleteMyStudy(study);
        deleteSnapshot(study);
        studyRepository.delete(study);
    }

    private void isLeader(Long leader, Long memberId) {
        if (leader != memberId)
            throw new NoPermissionException("권한이 없습니다.");
    }

    private void deleteMyStudy(Study study) {
        myStudyDeleteUseCase.study(study);
    }

    private void deleteSnapshot(Study study) {
        snapshotUseCase.deleteStudy(study);
    }
}
