package com.baeker.study.testUtil.global;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import com.baeker.study.study.domain.entity.Study;

public class CreateDomain {

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

    public static MyStudy createMyStudy(Long memberId, String msg, StudyStatus status) {
        Study study = Study.createStudy(1L, "study", "about", 10, 10L);
        MyStudy myStudy;

        switch (status) {
            case PENDING -> myStudy = MyStudy.joinStudy(memberId, study, msg);
            case INVITING -> myStudy = MyStudy.inviteStudy(memberId, study, msg);
            default -> myStudy = MyStudy.createNewStudy(memberId, study);
        }

        return myStudy;
    }
}