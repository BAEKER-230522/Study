package com.baeker.study.myStudy.in.reqDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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