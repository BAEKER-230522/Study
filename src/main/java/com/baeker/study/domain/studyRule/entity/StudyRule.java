package com.baeker.study.domain.studyRule.entity;

import com.baeker.study.domain.studyRule.dto.request.CreateStudyRuleRequest;
import com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.PersonalStudyRule;
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
import java.util.ArrayList;
import java.util.List;

import static com.baeker.study.domain.studyRule.entity.Status.FAIL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder(toBuilder = true)
@Getter
@EntityListeners(AuditingEntityListener.class)
//@Table(name = "study_rule",
//        indexes = {
//        @Index(name = "study_rule_idx", columnList = "study_id")
//        }
//)
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
    private boolean sendMail;
    private boolean addXp;

    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @OneToMany(mappedBy = "studyRule", cascade = CascadeType.ALL)
    @Builder.Default
    private List<PersonalStudyRule> personalStudyRules = new ArrayList<>();

    private Double xp;

    public void setStatus(boolean status) {
        if (status) {
            this.status = Status.COMPLETE;
        } else {
            this.status = FAIL;
        }
    }

    public void setMission(LocalDate date) {
        if (date.isBefore(startDate)) {
            this.mission = Mission.INACTIVE;
        }
        else if (date.isAfter(deadline)) {
            this.mission = Mission.DONE;
        }
        else {
            this.mission = Mission.ACTIVE;
        }
    }

    public static StudyRule create(CreateStudyRuleRequest request) {
        return builder()
                .name(request.getName())
                .startDate(request.getStartDate())
                .deadline(request.getDeadline())
                .about(request.getAbout())
                .xp(request.getXp())
                .status(FAIL)
                .addXp(false)
                .build();
    }

    public void setStudy(StudyRule studyRule, Study study) {
        this.study = study;
        study.getStudyRules().add(studyRule);
    }
    public void addPersonalStatus(PersonalStudyRule... personalStudyRule) {
        for (PersonalStudyRule personal : personalStudyRule) {
            if (personal.getStudyRule() == this) personalStudyRules.add(personal);
        }
    }

    public void setSendMail() {
        this.sendMail = true;
    }

    public void addXp() {
        this.study.xpUp(this.xp.intValue());
        this.addXp = true;
    }
}
