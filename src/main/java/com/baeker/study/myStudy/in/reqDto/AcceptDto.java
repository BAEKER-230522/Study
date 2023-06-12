package com.baeker.study.myStudy.in.reqDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AcceptDto {

    @Schema(example = "1")
    private Long id;
}
