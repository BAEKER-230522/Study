package com.baeker.study.myStudy.in.reqDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JoinMyStudyReqDto {

    @Schema(example = "1")
    private Long study;
    @Schema(example = "1")
    private Long member;
    @Schema(example = "가입 요청 메시지")
    private String msg;
}
