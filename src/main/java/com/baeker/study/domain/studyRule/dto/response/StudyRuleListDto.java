package com.baeker.study.domain.studyRule.dto.response;

import com.baeker.study.domain.studyRule.entity.StudyRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRuleListDto {

    private Long id;
    private String name;
    private String about;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Long ruleId;

    public StudyRuleListDto(StudyRule studyRule) {
        this.id = studyRule.getId();
        this.name = studyRule.getName();
        this.about = studyRule.getAbout();
        this.createDate = studyRule.getCreateDate();
        this.modifyDate = studyRule.getModifyDate();
        this.ruleId = studyRule.getRuleId();
    }
}
