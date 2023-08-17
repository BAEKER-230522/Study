package com.baeker.study.study.in.reqDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateLeaderReqDto {

    @Schema(example = "1")
    private Long studyId;
    @Schema(example = "기존 리더")
    private Long oldLeader;
    @Schema(example = "새로운 리더")
    private Long newLeader;
}