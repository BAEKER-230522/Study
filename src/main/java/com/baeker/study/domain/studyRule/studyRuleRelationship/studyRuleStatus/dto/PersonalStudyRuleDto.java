package com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.dto;

import com.baeker.study.domain.studyRule.entity.Status;
import com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.dto.ProblemStatusQueryDto;

import java.util.List;

public record PersonalStudyRuleDto(Long memberId, Status personalStudyRuleStatus, List<ProblemStatusQueryDto> problemStatusQueryDtos) {
}
