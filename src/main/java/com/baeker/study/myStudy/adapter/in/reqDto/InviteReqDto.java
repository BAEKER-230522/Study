package com.baeker.study.myStudy.adapter.in.reqDto;

import lombok.Data;

@Data
public class InviteReqDto {

    private Long studyId;
    private Long invitee;
    private String msg;
}
