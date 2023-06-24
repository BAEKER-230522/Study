package com.baeker.study.myStudy.in.resDto;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateMyStudyDto {

    @Schema(example = "1")
    private Long questionId;
    @Schema(example = "1")
    private Long myStudyId;
    @Schema(example = "1")
    private Long memberId;
    @Schema(example = "저장된 메시지")
    private String msg;
    @Schema(example = "PENDING")
    private StudyStatus status;

    public CreateMyStudyDto(MyStudy myStudy) {
        this.questionId = myStudy.getId();
        this.myStudyId = myStudy.getStudy().getId();
        this.memberId = myStudy.getMember();
        this.msg = myStudy.getMsg();
        this.status = myStudy.getStatus();
    }
}
