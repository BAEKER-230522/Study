package com.baeker.study.myStudy.in.reqDto;

import lombok.Data;

@Data
public class InviteMyStudyReqDto {

    private Long study;
    private Long inviter;
    private Long invitee;
    private String msg;
}
