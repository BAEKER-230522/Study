package com.baeker.study.studyRule.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyStudyRuleRequest {
    @NotEmpty
    @Schema(description = "변경하고싶은 StudyRule 의 이름(name) 작성", example = "이름수정")
    private String name;

    @Schema(description = "변경하고싶은 StudyRule 의 소개(about) 작성", example = "소개수정")
    @NotEmpty
    private String about;
}
