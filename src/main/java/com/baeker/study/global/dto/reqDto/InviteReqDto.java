package com.baeker.study.global.dto.reqDto;

import lombok.Data;

@Data
public class InviteReqDto {

    private Long studyId;
    private Long invitee;
    private String msg;
}
