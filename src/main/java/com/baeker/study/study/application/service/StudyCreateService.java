package com.baeker.study.study.application.service;

import com.baeker.study.global.exception.service.NoPermissionException;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.myStudy.application.port.in.MyStudyCreateUseCase;
import com.baeker.study.global.dto.reqDto.StudyCreateReqDto;
import com.baeker.study.study.application.port.in.SnapshotUseCase;
import com.baeker.study.study.application.port.in.StudyCreateUseCase;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.legacy.in.reqDto.BaekjoonDto;
import com.baeker.study.study.legacy.in.resDto.CreateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyCreateService implements StudyCreateUseCase {

    private final StudyRepositoryPort repository;
    private final MemberClient memberClient;
    private final MyStudyCreateUseCase myStudyCreateUseCase;
    private final SnapshotUseCase snapshotUseCase;

    @Override
    public CreateResDto study(Long memberId, StudyCreateReqDto dto) {
        permissionCheck(memberId);

        Study study = repository.save(
                Study.createStudy(
                        dto.getName(), dto.getAbout(),
                        dto.getCapacity(), memberId
                ));

        createSnapshot(study);
        Long myStudyId = myStudyCreateUseCase.myStudy(memberId, study);

        return new CreateResDto(study.getId(), myStudyId);
    }

    private void permissionCheck(Long memberId) {
        if (!memberClient.isConnectBaekJoon(memberId))
            throw new NoPermissionException("백준 연동이 안된 user");
    }

    private void createSnapshot(Study study) {
        BaekjoonDto dto = new BaekjoonDto(study);

        for (int i = -6; i < 1; i++)
            snapshotUseCase.createSnapshot(study, dto, i);
    }
}
