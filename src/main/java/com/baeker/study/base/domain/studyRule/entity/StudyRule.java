package com.baeker.study.base.domain.studyRule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@SuperBuilder(toBuilder = true)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class StudyRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String about;

    private Double rate;  // double 랭킹?
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
}
