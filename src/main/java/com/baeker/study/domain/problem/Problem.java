package com.baeker.study.domain.problem;

import com.baeker.study.domain.studyRule.entity.Status;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
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

    private Integer memberId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = LAZY)
    private StudyRule studyRule;

    protected static Problem createProblem(String problemName, Integer problemNumber, StudyRule studyRule) {
        return Problem.builder()
                .problemName(problemName)
                .problemNumber(problemNumber)
                .status(Status.FAIL)
                .studyRule(studyRule)
                .build();
    }

    public void isSuccess(boolean isSuccess) {
        if (isSuccess) this.status = Status.COMPLETE;
        else this.status = Status.FAIL;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
