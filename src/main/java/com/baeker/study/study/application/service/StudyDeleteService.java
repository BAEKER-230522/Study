package com.baeker.study.study.application.service;

import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.study.application.port.in.StudyDeleteUseCase;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyDeleteService implements StudyDeleteUseCase {

    private final StudyRepositoryPort studyRepositoryPort;

    @Override
    public void study(Study study, Long memberId) {
        isLeader(study.getLeader(), memberId);


    }

    private void isLeader(Long leader, Long memberId) {
        if (leader != memberId)
            throw new NoPermissionException("권한이 없습니다.");
    }
}
