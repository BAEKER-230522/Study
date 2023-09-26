package com.baeker.study.myStudy.application.port.in;

import com.baeker.study.study.domain.entity.Study;

public interface MyStudyCreateUseCase {

    Long myStudy(Long memberId, Study study);
}
