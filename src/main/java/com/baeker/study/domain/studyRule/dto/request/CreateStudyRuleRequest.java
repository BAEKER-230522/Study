package com.baeker.study.domain.studyRule.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateStudyRuleRequest {
    @NotEmpty
    @Schema(description = "StudyRule의 이름", example = "이름")
    private String name;
    @Schema(description = "StudyRule 소개", example = "소개")
    private String about;
    @Schema(description = "StudyRule 이 만들어질 스터디의 아이디(StudyId)", example = "1")
    private Long studyId;
    @Schema(description = "StudyRule 이 반영할 규칙 (RuleId)", example = "1")
    private Long ruleId;
}
