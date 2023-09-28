package com.baeker.study.testUtil;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.domain.entity.Study;

public class CreateStudy {

    public static Study createStudy(Long memberId, Long studyId, String name) {
        Study study = Study.createStudy(studyId, name, "about", 10, memberId);
        MyStudy myStudy = MyStudy.createNewStudy(memberId, study);
        study.getMyStudies().add(myStudy);
        return study;
    }

    public static Study createStudy(Long memberId, Long studyId, String name, int capacity) {
        Study study = Study.createStudy(studyId, name, "about", capacity, memberId);
        MyStudy myStudy = MyStudy.createNewStudy(memberId, study);
        study.getMyStudies().add(myStudy);
        return study;
    }
}