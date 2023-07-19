package com.baeker.study.domain.problem;

import com.baeker.study.domain.studyRule.entity.StudyRule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Problem {
    @Id
    @GeneratedValue
    private Long id;

    private String problemName;

    private Integer problemNumber;

    @ManyToOne(fetch = LAZY)
    private StudyRule studyRule;

    public static Problem createProblem(String problemName, Integer problemNumber, StudyRule studyRule) {
        return Problem.builder()
                .problemName(problemName)
                .problemNumber(problemNumber)
                .studyRule(studyRule)
                .build();
    }
}
