package com.baeker.study.myStudy.application.service;

import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.myStudy.application.port.in.MyStudyCreateUseCase;
import com.baeker.study.myStudy.application.port.out.persistence.MyStudyRepositoryPort;
import com.baeker.study.myStudy.domain.entity.MyStudy;
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
    public MyStudy myStudy(Long memberId, Study study) {
        MyStudy myStudy = repository.save(
                MyStudy.createNewStudy(memberId, study)
        );
        return null;
    }
}
