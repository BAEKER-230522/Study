package com.baeker.study.studyRule.studyRuleRelationship.studyRuleStatus.dto;

import com.baeker.study.studyRule.entity.Status;
import com.baeker.study.studyRule.studyRuleRelationship.problemStatus.dto.ProblemStatusQueryDto;

import java.util.List;

public record PersonalStudyRuleDto(Long memberId, Status personalStudyRuleStatus, List<ProblemStatusQueryDto> problemStatusQueryDtos) {
}
