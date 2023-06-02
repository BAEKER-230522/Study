package com.baeker.study.domain.studyRule.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ModifyStudyRuleRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String about;
    @NotEmpty
    private Long ruleId;
}
