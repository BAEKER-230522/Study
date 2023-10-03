package com.baeker.study.myStudy.in.resDto;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.MemberResDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class MyStudyResDto {

    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String msg;
    private StudyStatus status;
    private Long member;
    private Long study;
    private Integer memberRanking;
    private Integer studyRanking;

    public MyStudyResDto(MyStudy myStudy, MemberResDto member, Study study) {
        this.id = myStudy.getId();
        this.createDate = myStudy.getCreateDate();
        this.modifyDate = myStudy.getModifyDate();
        this.msg = myStudy.getMsg();
        this.status = myStudy.getStatus();
        this.member = myStudy.getMember();
        this.study = myStudy.getStudy().getId();
        this.memberRanking = member.getRanking();
        this.studyRanking = study.getRanking();
    }

    public MyStudyResDto(MyStudy myStudy, Integer memberRanking, Integer studyRanking) {
        this.id = myStudy.getId();
        this.createDate = myStudy.getCreateDate();
        this.modifyDate = myStudy.getModifyDate();
        this.msg = myStudy.getMsg();
        this.status = myStudy.getStatus();
        this.member = myStudy.getMember();
        this.study = myStudy.getStudy().getId();
        this.memberRanking = memberRanking;
        this.studyRanking = studyRanking;
    }
}
