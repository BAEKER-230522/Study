package com.baeker.study.study.application.service;

import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.myStudy.application.port.in.MyStudyCreateUseCase;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.application.port.in.StudyCreateUseCase;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.in.resDto.CreateResDto;
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
    private final MyStudyCreateUseCase myStudyCreateUseCase;

    @Override
    public CreateResDto study(Long memberId, CreateReqDto dto) {
        permissionCheck(memberId);

        Study study = repository.save(
                Study.createStudy(
                        dto.getName(), dto.getAbout(),
                        dto.getCapacity(), memberId
                ));
        MyStudy myStudy = myStudyCreateUseCase.myStudy(memberId, study);

        return new CreateResDto(study.getId(), myStudy.getId());
    }

    private void permissionCheck(Long memberId) {
        MemberResDto member = memberClient.findById(memberId).getData();

        if (member.getBaekJoonName() == null)
            throw new NoPermissionException("백준 연동이 안된 user");
    }
}
