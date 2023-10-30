package com.baeker.study.studyRule.studyRuleRelationship.studyRuleStatus.dto;

import com.baeker.study.studyRule.entity.Status;
import com.baeker.study.studyRule.studyRuleRelationship.problemStatus.dto.ProblemStatusQueryDto;

import java.util.List;

public record PersonalStudyRuleResponse(String nickName, String memberId, Status personalStudyRuleStatus,
                                        List<ProblemStatusQueryDto> problemStatusQueryDtos
) {
    public PersonalStudyRuleResponse(String nickName, String memberId, PersonalStudyRuleDto ruleDto) {
        this(nickName,memberId, ruleDto.personalStudyRuleStatus(), ruleDto.problemStatusQueryDtos());
    }
}
