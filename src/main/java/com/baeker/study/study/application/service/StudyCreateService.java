package com.baeker.study.study.application.service;

import com.baeker.study.base.exception.NoPermissionException;
import com.baeker.study.study.application.port.in.StudyCreateUseCase;
import com.baeker.study.study.application.port.out.apiClient.MemberClient;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.in.resDto.MemberResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyCreateService implements StudyCreateUseCase {

    private final StudyRepositoryPort repository;
    private final MemberClient memberClient;

    @Override
    public Study study(Long memberId, CreateReqDto dto) {
        permissionCheck(memberId);

        Study study = Study.createStudy(
                dto.getName(), dto.getAbout(),
                dto.getCapacity(), memberId
        );
        return repository.save(study);
    }

    private void permissionCheck(Long memberid) {
        MemberResDto member = memberClient.byId(memberid).getData();

        if (member.getBaekJoonName() == null)
            throw new NoPermissionException("백준 연동이 안된 user");
    }
}
