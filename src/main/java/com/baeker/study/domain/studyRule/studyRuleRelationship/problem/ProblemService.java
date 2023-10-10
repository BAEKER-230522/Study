package com.baeker.study.domain.studyRule.studyRuleRelationship.problem;

import com.baeker.study.base.error.exception.NotFoundException;
import com.baeker.study.domain.studyRule.studyRuleRelationship.problem.dto.CreateProblem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Transactional
    public List<Long> createProblem(List<CreateProblem> createProblems) {
        List<Long> problemIds = new ArrayList<>();
        for (CreateProblem createProblem : createProblems) {
            Problem problem = Problem.createProblem(createProblem.problemName(), createProblem.problemNumber());
            problemRepository.save(problem);
            problemIds.add(problem.getId());
        }
        return problemIds;
    }

    public Problem getProblem(Long problemId) {
        return problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException("해당 문제가 존재하지 않습니다."));
    }

    @Transactional
    public void deleteProblem(Long problemId) {
        problemRepository.deleteById(problemId);
    }

}
