package com.baeker.study.testUtil;

import com.baeker.study.study.domain.entity.Study;

public class CreateStudy {

    public static Study CreateStudy(Long memberId, Long studyId, String name) {
        return Study.createStudy(studyId, name, "about", 10, memberId);
    }
}
