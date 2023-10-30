package com.baeker.study.studyRule.studyRuleRelationship.studyRuleStatus.dto;

import com.baeker.study.studyRule.entity.Status;
import com.baeker.study.studyRule.studyRuleRelationship.problemStatus.dto.ProblemStatusQueryDto;

import java.util.List;

public record PersonalStudyRuleResponse(String nickName, Status personalStudyRuleStatus,
                                        List<ProblemStatusQueryDto> problemStatusQueryDtos) {
    public PersonalStudyRuleResponse(String nickName, PersonalStudyRuleDto ruleDto) {
        this(nickName, ruleDto.personalStudyRuleStatus(), ruleDto.problemStatusQueryDtos());
    }
}
