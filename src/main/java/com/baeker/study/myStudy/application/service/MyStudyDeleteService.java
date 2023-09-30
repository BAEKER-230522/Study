package com.baeker.study.myStudy.application.service;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.myStudy.application.port.in.MyStudyDeleteUseCase;
import com.baeker.study.myStudy.application.port.out.persistence.MyStudyRepositoryPort;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.out.reqDto.DeleteMyStudyReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyStudyDeleteService implements MyStudyDeleteUseCase {

    private final MyStudyRepositoryPort repository;
    private final MemberClient memberClient;

    @Override
    public void myStudy(Long memberId, MyStudy myStudy) {



        deleteMember(myStudy);
    }

    @Override
    public void dropOut(Long memberId, MyStudy myStudy) {


        deleteMember(myStudy);
    }

    private void deleteMember(MyStudy myStudy) {
        DeleteMyStudyReqDto dto = new DeleteMyStudyReqDto(
                myStudy.getMember(),
                myStudy.getId()
        );
        memberClient.deleteMyStudy(dto);
        repository.delete(myStudy);
    }
}
