package com.baeker.study.myStudy.application.service;

import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.myStudy.application.port.in.MyStudyCreateUseCase;
import com.baeker.study.myStudy.application.port.out.persistence.MyStudyRepositoryPort;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.out.reqDto.CreateMyStudyReqDto;
import com.baeker.study.study.domain.entity.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyStudyCreateService implements MyStudyCreateUseCase {

    private final MyStudyRepositoryPort repository;
    private final MemberClient memberClient;

    @Override
    public Long myStudy(Long memberId, Study study) {
        MyStudy my = MyStudy.createNewStudy(memberId, study);
        MyStudy myStudy = repository.save(my);
        return updateMember(myStudy);
    }

    private Long updateMember(MyStudy myStudy) {
        CreateMyStudyReqDto reqDto = new CreateMyStudyReqDto(myStudy.getMember(), myStudy.getId());
        memberClient.updateMyStudy(reqDto);
        return myStudy.getId();
    }
}
