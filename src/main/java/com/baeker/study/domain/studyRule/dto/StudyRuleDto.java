package com.baeker.study.domain.studyRule.dto;

import com.baeker.study.domain.studyRule.studyRuleRelationship.problem.dto.ProblemDto;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.PersonalStudyRule;
import com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.dto.PersonalStudyRuleDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Schema(description = "StudyRule 이 가지고 있는 ProblemDto 리스트")
    private List<PersonalStudyRuleDto> personalStudyRuleDtos;

    public StudyRuleDto(StudyRule studyRule) {
        this.id = studyRule.getId();
        this.name = studyRule.getName();
        this.about = studyRule.getAbout();
        this.study = new StudyResDto(studyRule.getStudy());
        this.ruleId = studyRule.getRuleId();
        this.personalStudyRuleDtos = personalStudyRuleDtos(studyRule.getPersonalStudyRules());
    }

    private List<PersonalStudyRuleDto> personalStudyRuleDtos(List<PersonalStudyRule> personalStudyRules) {
        return personalStudyRules.stream().map(entity -> {
            List<ProblemDto> problemDtos = entity.getProblemStatuses().stream()
                    .map(problemStatus -> new ProblemDto(problemStatus.getProblem().getProblemName(),
                                                    problemStatus.getProblem().getProblemNumber())).toList();
        return new PersonalStudyRuleDto(entity.getMemberId(), entity.getStatus(), problemDtos);
        }).toList();
    }
}
