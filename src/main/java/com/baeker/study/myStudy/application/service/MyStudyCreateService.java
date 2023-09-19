package com.baeker.study.myStudy.application.service;

import com.baeker.study.myStudy.application.port.in.MyStudyCreateUseCase;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.domain.entity.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyStudyCreateService implements MyStudyCreateUseCase {
    @Override
    public MyStudy myStudy(Long memberId, Study study) {


        return null;
    }
}
