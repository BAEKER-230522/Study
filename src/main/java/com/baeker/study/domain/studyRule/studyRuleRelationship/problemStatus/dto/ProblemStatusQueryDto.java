package com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.dto;

import com.baeker.study.domain.studyRule.entity.Status;

public record ProblemStatusQueryDto(
        Long memberId,
        Integer problemNumber,
        String problemName,
        Status problemStatus,
        int time,
        int memory

)
{

}
