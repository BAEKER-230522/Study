package com.baeker.study.myStudy.domain.entity;

import com.baeker.study.base.entity.BaseEntity;
import com.baeker.study.study.domain.entity.Study;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.baeker.study.myStudy.domain.entity.StudyStatus.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
public class MyStudy extends BaseEntity {

    private String msg;
    @Enumerated(STRING)
    private StudyStatus status;
    private Long member;

    @ManyToOne(fetch = LAZY)
    private Study study;


    //-- create method --//

    // 새로운 스터디 만들 때 //
    public static MyStudy createNewStudy(Long member, Study study) {
        MyStudy myStudy = create(member, study);
        myStudy.status = MEMBER;
        study.getMyStudies().add(myStudy);
        return myStudy;
    }

    // 스터디 가입할 때 //
    public static MyStudy joinStudy(Long member, Study study, String msg) {
        MyStudy myStudy = create(member, study);
        myStudy.status = PENDING;
        myStudy.msg = msg;

        study.getMyStudies().add(myStudy);
        return myStudy;
    }

    // 스터디로 초대할 때 //
    public static MyStudy inviteStudy(Long member, Study study, String msg) {
        MyStudy myStudy = create(member, study);
        myStudy.status = INVITING;
        myStudy.msg = msg;

        study.getMyStudies().add(myStudy);
        return myStudy;
    }

    // my study 생성 //
    private static MyStudy create(Long member, Study study) {
        return builder()
                .member(member)
                .study(study)
                .build();
    }


    //-- business logic --//

    // modify msg //
    public void modifyMsg(String msg) {
        this.msg = msg;
    }

    // accept //
    public void accept() {
        this.status = MEMBER;
        this.msg = null;
    }
}
