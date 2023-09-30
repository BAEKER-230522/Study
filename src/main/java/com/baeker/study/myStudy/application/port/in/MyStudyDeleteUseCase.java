package com.baeker.study.myStudy.application.port.in;

import com.baeker.study.myStudy.domain.entity.MyStudy;

public interface MyStudyDeleteUseCase {

    void myStudy(Long memberId, MyStudy myStudy);

    void dropOut(Long memberId, MyStudy myStudy);
}
