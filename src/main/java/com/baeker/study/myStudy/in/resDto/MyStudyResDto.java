package com.baeker.study.myStudy.in.resDto;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
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

    public MyStudyResDto(MyStudy myStudy) {
        this.id = myStudy.getId();
        this.createDate = myStudy.getCreateDate();
        this.modifyDate = myStudy.getModifyDate();
        this.msg = myStudy.getMsg();
        this.status = myStudy.getStatus();
        this.member = myStudy.getMember();
        this.study = myStudy.getStudy().getId();
    }
}
