package com.baeker.study.myStudy.in.resDto;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import lombok.Data;

@Data
public class CreateMyStudyDto {

    private Long questionId;
    private Long MyStudyId;
    private Long memberId;
    private String msg;
    private StudyStatus status;

    public CreateMyStudyDto(MyStudy myStudy) {
        this.questionId = myStudy.getId();
        this.MyStudyId = myStudy.getStudy().getId();
        this.memberId = myStudy.getMember();
        this.msg = myStudy.getMsg();
        this.status = myStudy.getStatus();
    }
}
