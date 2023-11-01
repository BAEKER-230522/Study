package com.baeker.study.myStudy.legacy.in.reqDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class JoinMyStudyReqDto {

    @Schema(example = "1")
    private Long study;
    @Schema(example = "1")
    private Long member;
    @Schema(example = "가입 요청 메시지")
    private String msg;
}