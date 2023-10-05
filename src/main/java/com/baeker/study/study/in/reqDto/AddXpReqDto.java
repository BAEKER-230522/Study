package com.baeker.study.study.in.reqDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddXpReqDto {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "10")
    private double xp;
}
