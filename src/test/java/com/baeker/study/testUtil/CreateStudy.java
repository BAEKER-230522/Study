package com.baeker.study.testUtil;

import com.baeker.study.study.domain.entity.Study;

public class CreateStudy {

    public static Study CreateStudy(String name, Long memberId) {
        return Study.createStudy(1L,name, "about", 10, memberId);
    }
}
