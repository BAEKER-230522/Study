package com.baeker.study.domain.studyRule.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreateStudyRuleRequest {
    @NotEmpty
    private String name;
    private String about;

    private Long studyId;
    private Long ruleId;
}
