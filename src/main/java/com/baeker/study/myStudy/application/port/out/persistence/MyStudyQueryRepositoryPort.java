package com.baeker.study.myStudy.application.port.out.persistence;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.domain.entity.Study;

import java.util.List;

public interface MyStudyQueryRepositoryPort {

    MyStudy byStudyIdAndMemberId(Long memberId, Study study);

    List<MyStudy> byMemberId(Long studyId, Long memberId);
}
