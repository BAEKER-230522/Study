package com.baeker.study.studyRule.studyRuleRelationship.problemStatus.dto;

import com.baeker.study.studyRule.entity.Status;

public record ProblemStatusQueryDto(
        Long problemStatusId,
        Long memberId,
        Integer problemNumber,
        String problemName,
        Status problemStatus,
        int time,
        int memory

)
{

}
