package com.baeker.study.domain.problem;

import com.baeker.study.domain.studyRule.entity.StudyRule;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
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

    protected static Problem createProblem(String problemName, Integer problemNumber, StudyRule studyRule) {
        return Problem.builder()
                .problemName(problemName)
                .problemNumber(problemNumber)
                .studyRule(studyRule)
                .build();
    }
}
