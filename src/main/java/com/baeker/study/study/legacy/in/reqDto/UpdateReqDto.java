package com.baeker.study.study.legacy.in.reqDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateReqDto {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "스터디 이름")
    private String name;
    @Schema(example = "스터디 소개")
    private String about;
    @Schema(example = "10")
    private Integer capacity;
}