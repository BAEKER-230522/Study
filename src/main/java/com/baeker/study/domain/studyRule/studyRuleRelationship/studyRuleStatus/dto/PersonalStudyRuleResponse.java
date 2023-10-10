package com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.dto;

import com.baeker.study.domain.studyRule.entity.Status;
import com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.dto.ProblemStatusQueryDto;

import java.util.List;

public record PersonalStudyRuleResponse(String nickName, Status personalStudyRuleStatus,
                                        List<ProblemStatusQueryDto> problemStatusQueryDtos) {
    public PersonalStudyRuleResponse(String nickName, PersonalStudyRuleDto ruleDto) {
        this(nickName, ruleDto.personalStudyRuleStatus(), ruleDto.problemStatusQueryDtos());
    }
}
