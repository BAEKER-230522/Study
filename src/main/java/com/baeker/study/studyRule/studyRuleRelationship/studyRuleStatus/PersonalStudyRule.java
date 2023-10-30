package com.baeker.study.studyRule.studyRuleRelationship.studyRuleStatus;

import com.baeker.study.studyRule.studyRuleRelationship.problemStatus.ProblemStatus;
import com.baeker.study.studyRule.entity.Status;
import com.baeker.study.studyRule.entity.StudyRule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class PersonalStudyRule {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long memberId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_rule_id")
    private StudyRule studyRule;
    @OneToMany(mappedBy = "personalStudyRule", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProblemStatus> problemStatuses = new ArrayList<>();

    public static PersonalStudyRule create(Long memberId, StudyRule studyRule) {
        return PersonalStudyRule.builder()
                .memberId(memberId)
                .status(Status.FAIL)
                .studyRule(studyRule)
                .build();
    }
    public void addProblemStatus(ProblemStatus... problemStatus) {
        for (ProblemStatus status : problemStatus) {
            if (this == status.getPersonalStudyRule()) problemStatuses.add(status);
        }
    }

    public void addStudyRule(PersonalStudyRule personalStudyRule) {
        personalStudyRule.getStudyRule().addPersonalStatus(personalStudyRule);
    }

    /**
     * 미션에 있는 문제 모두 성공시 COMPLETE
     */
    public void isSuccessCheck() {
        long count = problemStatuses.stream()
                .map(ProblemStatus::getStatus)
                .filter(status -> status.equals(Status.FAIL)).count();
        if (count == 0) this.status = Status.COMPLETE;
        else this.status = Status.FAIL;
    }
}
