package com.baeker.study.domain.studyRule.dto.response;

import com.baeker.study.domain.studyRule.entity.StudyRule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRuleListDto {
    @Schema(description = "스터디 룰 아이디", example = "1")
    private Long id;
    @Schema(description = "스터디 룰 이름", example = "스터디 룰 이름")
    private String name;
    @Schema(description = "스터디 룰 설명",example = "스터디 룰 설명")
    private String about;
    @Schema(description = "스터디 룰 생성일", example = "2021-10-10 10:10:10")
    private LocalDateTime createDate;
    @Schema(description = "스터디 룰 수정일", example = "2021-10-10 10:10:10")
    private LocalDateTime modifyDate;
    @Schema(description = "적용한 규칙 아이디", example = "1")
    private Long ruleId;

    public StudyRuleListDto(StudyRule studyRule) {
        this.id = studyRule.getId();
        this.name = studyRule.getName();
        this.about = studyRule.getAbout();
        this.createDate = studyRule.getCreateDate();
        this.modifyDate = studyRule.getModifyDate();
        this.ruleId = studyRule.getRuleId();
    }
}
