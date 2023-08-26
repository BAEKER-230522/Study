package com.baeker.study.domain.studyRule.dto;

import com.baeker.study.study.domain.entity.Study;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyRuleForm {

    @NotBlank
    @Size(min = 2, max = 10)
    private String name;
    @Size(max = 30)
    private String about;

    @NotEmpty
    private Study study;
}
