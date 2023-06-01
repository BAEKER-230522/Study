package com.baeker.study.domain.studyRule.entity;

import com.baeker.study.domain.studyRule.Mission;
import com.baeker.study.domain.studyRule.dto.StudyRuleForm;
import com.baeker.study.domain.studyRule.dto.request.CreateStudyRuleRequest;
import com.baeker.study.study.domain.entity.Study;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    public void setMission(boolean mission) {
        if (mission) {
            this.mission = Mission.COMPLETE;
        } else {
            this.mission = Mission.FAIL;
        }
    }

    public static StudyRule create(CreateStudyRuleRequest request) {
        return builder()
                .name(request.getName())
                .about(request.getAbout())
                .ruleId(request.getRuleId())
                .mission(Mission.FAIL)
                .build();
    }

    public void setStudy(StudyRule studyRule, Study study) {
        this.study = study;
        study.getStudyRules().add(studyRule);
    }
}
