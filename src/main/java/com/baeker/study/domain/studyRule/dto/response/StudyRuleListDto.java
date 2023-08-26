package com.baeker.study.domain.studyRule.dto.response;

import com.baeker.study.domain.studyRule.entity.Mission;
import com.baeker.study.domain.studyRule.entity.Status;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRuleListDto {
    @Schema(description = "미션 아이디", example = "1")
    private Long id;
    @Schema(description = "미션 이름", example = "이름")
    private String name;
    @Schema(description = "미션 설명",example = "설명")
    private String about;
    @Schema(description = "미션 생성일", example = "2021-10-10 10:10:10")
    private LocalDateTime createDate;
    @Schema(description = "미션 수정일", example = "2021-10-10 10:10:10")
    private LocalDateTime modifyDate;

    @Schema(description = "미션 활성화 여부", example = "ACTIVE")
    private Mission mission;
    @Schema(description = "미션 성공 여부", example = "COMPLETE")
    private Status status;

    @Schema(description = "미션 시작일", example = "2021-10-10")
    private LocalDate startDate;

    @Schema(description = "미션 종료일", example = "2021-10-10")
    private LocalDate deadline;

//    @Schema(description = "미션 문제에 대한 상태")
//    private List<PersonalStudyRuleDto> personalStudyRuleDtos = new ArrayList<>();

    public StudyRuleListDto(StudyRule studyRule) {
        this.id = studyRule.getId();
        this.name = studyRule.getName();
        this.about = studyRule.getAbout();
        this.createDate = studyRule.getCreateDate();
        this.modifyDate = studyRule.getModifyDate();
        this.mission = studyRule.getMission();
        this.status = studyRule.getStatus();
        this.startDate = studyRule.getStartDate();
        this.deadline = studyRule.getDeadline();
//        this.personalStudyRuleDtos = personalStudyRuleDtos(studyRule.getPersonalStudyRules());
    }


}
