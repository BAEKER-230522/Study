package com.baeker.study.domain.studyRule.dto;

import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.study.in.resDto.StudyResDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRuleDto {
    private Long id;
    private String name;
    private String about;
    private StudyResDto study;
    private Long ruleId;

    public StudyRuleDto(StudyRule studyRule) {
        this.name = studyRule.getName();
        this.about = studyRule.getAbout();
        this.study = new StudyResDto(studyRule.getStudy());
        this.ruleId = studyRule.getRuleId();
    }


}
