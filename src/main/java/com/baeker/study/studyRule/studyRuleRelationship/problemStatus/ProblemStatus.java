package com.baeker.study.studyRule.studyRuleRelationship.problemStatus;

import com.baeker.study.studyRule.studyRuleRelationship.problem.Problem;
import com.baeker.study.studyRule.entity.Status;
import com.baeker.study.studyRule.studyRuleRelationship.studyRuleStatus.PersonalStudyRule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private int time;
    private int memory;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public static ProblemStatus create(PersonalStudyRule personalStudyRule, Problem problem) {
        return ProblemStatus.builder()
                .status(Status.FAIL)
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
        if (success) this.status = Status.COMPLETE;
        else this.status = Status.FAIL;
    }

    public void addMemory(int memory) {
        this.memory = memory;
    }

    public void addTime(int time) {
        this.time = time;
    }
}
