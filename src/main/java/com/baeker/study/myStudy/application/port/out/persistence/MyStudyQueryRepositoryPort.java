package com.baeker.study.myStudy.application.port.out.persistence;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.domain.entity.Study;

public interface MyStudyQueryRepositoryPort {

    MyStudy byStudyIdAndMemberId(Long memberId, Study study);
}
