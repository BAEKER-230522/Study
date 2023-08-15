package com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus;

import com.baeker.study.domain.studyRule.studyRuleRelationship.problem.Problem;
import com.baeker.study.domain.studyRule.entity.Status;
import com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.PersonalStudyRule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.baeker.study.domain.studyRule.entity.Status.COMPLETE;
import static com.baeker.study.domain.studyRule.entity.Status.FAIL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
@Builder(toBuilder = true)
@Table(name = "problem_status")
public class ProblemStatus {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    private Status status;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_rule_status_id")
    private PersonalStudyRule personalStudyRule;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public static ProblemStatus create(PersonalStudyRule personalStudyRule, Problem problem) {
        return ProblemStatus.builder()
                .status(FAIL)
                .personalStudyRule(personalStudyRule)
                .problem(problem)
                .build();
    }

    public void addProblem() {
        problem.addProblemStatus(this);
    }
    public void addPersonalStudyRule() {
        personalStudyRule.addProblemStatus(this);
    }

    public void updateStatus(boolean success) {
        if (success) this.status = COMPLETE;
        else this.status = FAIL;
    }
}
