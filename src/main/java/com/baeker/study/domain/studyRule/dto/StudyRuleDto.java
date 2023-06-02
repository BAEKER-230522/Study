package com.baeker.study.domain.studyRule.dto;

import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.study.domain.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRuleDto {
    private String name;
    private String about;
    private Study study;
    private Long ruleId;

    public StudyRuleDto(StudyRule studyRule) {
        name = studyRule.getName();
        about = studyRule.getAbout();
        study = studyRule.getStudy();
        ruleId = studyRule.getRuleId();
    }
}
