package com.baeker.study.myStudy.in.reqDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ModifyMsgDto {

    @Schema(example = "1")
    private Long memberId;

    @Schema(example = "1")
    private Long studyId;

    @Schema(example = "업데이트 메시지")
    private String msg;
}
