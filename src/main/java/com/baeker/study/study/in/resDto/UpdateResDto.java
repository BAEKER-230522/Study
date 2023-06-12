package com.baeker.study.study.in.resDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateResDto {

    @Schema(example = "1")
    private Long id;
}
