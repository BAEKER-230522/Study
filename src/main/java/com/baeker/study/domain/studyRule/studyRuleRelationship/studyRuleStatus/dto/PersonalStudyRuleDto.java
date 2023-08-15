package com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.dto;

import com.baeker.study.domain.studyRule.studyRuleRelationship.problem.dto.ProblemDto;
import com.baeker.study.domain.studyRule.entity.Status;

import java.util.List;

public record PersonalStudyRuleDto(Long memberId, Status status, List<ProblemDto> problemDto) {
}
