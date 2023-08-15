package com.baeker.study.domain.studyRule.studyRuleRelationship.problem;

import com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.ProblemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Problem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String problemName;

    private Integer problemNumber;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProblemStatus> problemStatuses = new ArrayList<>();

    public static Problem createProblem(String problemName, Integer problemNumber) {
        return Problem.builder()
                .problemName(problemName)
                .problemNumber(problemNumber)
                .build();
    }
    public void addProblemStatus(ProblemStatus... problemStatus) {
        for (ProblemStatus status : problemStatus) {
            if (status.getProblem() == this) problemStatuses.add(status);
        }
    }
}
