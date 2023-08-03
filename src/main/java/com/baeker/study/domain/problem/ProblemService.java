package com.baeker.study.domain.problem;

import com.baeker.study.domain.problem.dto.CreateProblem;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Transactional
    public void createProblem(List<CreateProblem> createProblems, StudyRule studyRule) {
        for (CreateProblem createProblem : createProblems) {
            Problem problem = Problem.createProblem(createProblem.problemName(), createProblem.problemNumber(), studyRule);
            studyRule.getProblems().add(problem);
            problemRepository.save(problem);
        }
    }

}
