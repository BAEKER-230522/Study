package com.baeker.study.domain.studyRule.entity;

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

import java.time.LocalDate;
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

    private LocalDate startDate;
    private LocalDate deadline;
    @Enumerated(EnumType.STRING)
    private Mission mission;

    private Long ruleId;

    @Enumerated(EnumType.STRING)
    private Status status;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    public void setStatus(boolean status) {
        if (status) {
            this.status = Status.COMPLETE;
        } else {
            this.status = Status.FAIL;
        }
    }

    public void setMission(LocalDate date) {
        if (date.isBefore(startDate)) {
            this.mission = Mission.INACTIVE;
        }
        else if (date.isAfter(deadline)) {
            this.mission = Mission.DONE;
        }
        else if ((date.isAfter(startDate) || date.isEqual(startDate))&& date.isBefore(deadline)) {
            this.mission = Mission.ACTIVE;
        }
    }

    public static StudyRule create(CreateStudyRuleRequest request) {
        return builder()
                .name(request.getName())
                .startDate(request.getStartDate())
                .deadline(request.getDeadline())
                .about(request.getAbout())
                .ruleId(request.getRuleId())
                .status(Status.FAIL)
                .build();
    }

    public void setStudy(StudyRule studyRule, Study study) {
        this.study = study;
        study.getStudyRules().add(studyRule);
    }
}
