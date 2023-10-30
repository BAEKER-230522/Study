package com.baeker.study.studyRule.dto.query;

import com.baeker.study.studyRule.entity.Mission;
import com.baeker.study.studyRule.entity.Status;
import com.baeker.study.studyRule.entity.StudyRule;
import com.baeker.study.studyRule.studyRuleRelationship.studyRuleStatus.dto.PersonalStudyRuleDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRuleQueryDto {
    @Schema(description = "StudyRuleId", example = "1")
    private Long id;
    @Schema(description = "StudyRule 의 이름(name)", example = "이름")
    private String name;
    @Schema(description = "StudyRule 의 소개(about)", example = "소개")
    private String about;
    @Schema(description = "StudyRule 과 양방향 맵핑 되어있는 Study 의 정보")
    private StudyResDto study;

    @Schema(description = "미션 성공시 얻는 경험치")
    private Double xp;

    @Schema(description = "미션 성공실패 여부")
    private Status status;

    @Schema(description = "미션 시작 일", example = "2021-10-10")
    private LocalDate startDate;

    @Schema(description = "미션 종료 일", example = "2021-10-10")
    private LocalDate deadline;

    @Schema(description = "미션 활성화 여부")
    private Mission mission;
    @Schema(description = "StudyRule 이 가지고 있는 ProblemDto 리스트")
    private List<PersonalStudyRuleDto> personalStudyRuleDtos;


    public StudyRuleQueryDto(StudyRule studyRule, List<PersonalStudyRuleDto> personalStudyRuleDtos) {
        this.id = studyRule.getId();
        this.name = studyRule.getName();
        this.about = studyRule.getAbout();
        this.xp = studyRule.getXp();
        this.study = new StudyResDto(studyRule.getStudy());
        this.mission = studyRule.getMission();
        this.status = studyRule.getStatus();
        this.startDate = studyRule.getStartDate();
        this.deadline = studyRule.getDeadline();
        this.personalStudyRuleDtos = personalStudyRuleDtos;
    }
}
