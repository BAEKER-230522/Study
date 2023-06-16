package com.baeker.study.study.in.reqDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateLeaderReqDto {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "스터디 리더")
    private String leader;
}
