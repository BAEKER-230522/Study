package com.baeker.study.myStudy.application.port.in;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.domain.entity.Study;

public interface MyStudyDeleteUseCase {

    void myStudy(Long memberId, MyStudy myStudy);

    void study(Study study);

    void dropOut(Long memberId, MyStudy myStudy);
}
