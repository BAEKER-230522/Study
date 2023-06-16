package com.baeker.study.domain.studyRule.dto;

import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.study.in.resDto.StudyResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRuleDto {
    @Schema(description = "StudyRuleId", example = "1")
    private Long id;
    @Schema(description = "StudyRule 의 이름(name)", example = "이름")
    private String name;
    @Schema(description = "StudyRule 의 소개(about)", example = "소개")
    private String about;
    @Schema(description = "StudyRule 과 양방향 맵핑 되어있는 Study 의 정보")
    private StudyResDto study;
    @Schema(description = "StudyRule 이 의존하는 RuleId", example = "1")
    private Long ruleId;

    public StudyRuleDto(StudyRule studyRule) {
        this.id = studyRule.getId();
        this.name = studyRule.getName();
        this.about = studyRule.getAbout();
        this.study = new StudyResDto(studyRule.getStudy());
        this.ruleId = studyRule.getRuleId();
    }


}
