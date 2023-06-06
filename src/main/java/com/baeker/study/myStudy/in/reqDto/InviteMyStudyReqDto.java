package com.baeker.study.myStudy.in.reqDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class InviteMyStudyReqDto {

    @Schema(example = "1")
    private Long study;
    @Schema(example = "1")
    private Long inviter;
    @Schema(example = "1")
    private Long invitee;
    @Schema(example = "초대 메시지")
    private String msg;
}
