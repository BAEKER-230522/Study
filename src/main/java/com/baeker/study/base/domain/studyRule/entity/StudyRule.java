package com.baeker.study.base.domain.studyRule.entity;

import com.baeker.study.base.domain.studyRule.dto.StudyRuleForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder(toBuilder = true)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class StudyRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String about;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    @Enumerated(EnumType.STRING)
    private Mission mission;

    private Long ruleId;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    protected void setMission(boolean mission) {
        if (mission) {
            this.mission = Mission.COMPLETE;
        } else {
            this.mission = Mission.FAIL;
        }
    }

    public static StudyRule create(StudyRuleForm studyRuleForm, Long ruleId) {
        return builder()
                .name(studyRuleForm.getName())
                .about(studyRuleForm.getAbout())
                .ruleId(ruleId)
                .mission(Mission.FAIL)
                .build();
    }

    public void setStudy(Study study) {
        this.study = study;
        study.getStudyRules.add(this);
    }
}
