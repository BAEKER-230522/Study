package com.baeker.study.domain.studyRule.dto.request;

import com.baeker.study.domain.studyRule.studyRuleRelationship.problem.dto.CreateProblem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateStudyRuleRequest {
    @NotEmpty
    @Schema(description = "StudyRule의 이름", example = "이름")
    private String name;
    @Schema(description = "StudyRule 소개", example = "소개")
    private String about;

    @Schema(description = "미션 시작 일 ", example = "2021-08-01")
    private LocalDate startDate;

    @Schema(description = "미션 마감 일 ", example = "2021-08-01")
    private LocalDate deadline;
    @Schema(description = "StudyRule 이 만들어질 스터디의 아이디(StudyId)", example = "1")
    private Long studyId;
    @Schema(description = "StudyRule 이 반영할 규칙 (RuleId)", example = "1")
    private Long ruleId;

//    @Schema(description = "studyRule 멤버, 문제별 상태")
//    private List<StudyRuleStatus> statuses;
    @Schema(description = "미션에서 풀 문제")
    private List<CreateProblem> createProblemList;
}
