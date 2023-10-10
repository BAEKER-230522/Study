package com.baeker.study.domain.studyRule.dto.response;

import com.baeker.study.domain.studyRule.dto.query.StudyRuleQueryDto;
import com.baeker.study.domain.studyRule.entity.Mission;
import com.baeker.study.domain.studyRule.entity.Status;
import com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.dto.PersonalStudyRuleResponse;
import com.baeker.study.study.in.resDto.StudyResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyRuleDetailResponse {
    @Schema(description = "StudyRuleId", example = "1")
    private Long id;
    @Schema(description = "StudyRule 의 이름(name)", example = "이름")
    private String studyRuleName;
    @Schema(description = "StudyRule 의 소개(about)", example = "소개")
    private String about;
    @Schema(description = "StudyRule 과 양방향 맵핑 되어있는 Study 의 정보")
    private StudyResDto study;

    @Schema(description = "미션 성공실패 여부")
    private Status status;

    @Schema(description = "미션 시작 일", example = "2021-10-10")
    private LocalDate startDate;

    @Schema(description = "미션 종료 일", example = "2021-10-10")
    private LocalDate deadline;

    @Schema(description = "미션 활성화 여부")
    private Mission mission;
    @Schema(description = "StudyRule 이 가지고 있는 ProblemDto 리스트")
    private List<PersonalStudyRuleResponse> personalStudyRuleDtos;

    public StudyRuleDetailResponse(StudyRuleQueryDto studyRuleQueryDto, List<PersonalStudyRuleResponse> personalStudyRuleDtos) {
        this.id = studyRuleQueryDto.getId();
        this.studyRuleName = studyRuleQueryDto.getName();
        this.about = studyRuleQueryDto.getAbout();
        this.study = studyRuleQueryDto.getStudy();
        this.mission = studyRuleQueryDto.getMission();
        this.status = studyRuleQueryDto.getStatus();
        this.startDate = studyRuleQueryDto.getStartDate();
        this.deadline = studyRuleQueryDto.getDeadline();
        this.personalStudyRuleDtos = personalStudyRuleDtos;
    }
}
