package com.baeker.study.myStudy.application.port.in;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.domain.entity.Study;

public interface MyStudyCreateUseCase {

    MyStudy myStudy(Long memberId, Study study);
}
