package com.baeker.study.studyRule.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateStudyRuleResponse {
    @Schema(description = "StudyRuleId", example = "1")
    private Long id;
}
