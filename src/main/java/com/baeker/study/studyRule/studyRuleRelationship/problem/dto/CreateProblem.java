package com.baeker.study.studyRule.studyRuleRelationship.problem.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateProblem(
        @Schema(description = "문제 이름", example = "A+B")
        String problemName,
        @Schema(description = "문제 번호", example = "1000")
        Integer problemNumber) {
}
