package com.baeker.study.domain.studyRule.repository;

import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.baeker.study.domain.problem.QProblem.problem;
import static com.baeker.study.domain.studyRule.entity.QStudyRule.studyRule;
import static com.baeker.study.study.domain.entity.QStudy.study;

@Repository
@RequiredArgsConstructor
public class StudyRuleDslRepositoryImp implements StudyRuleDslRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StudyRule> findStudyRuleFromStudy(Long studyId) {
        return jpaQueryFactory.selectFrom(studyRule)
                .leftJoin(studyRule.study, study)
                .leftJoin(studyRule.problems, problem)
                .fetchJoin()
                .where(study.id.eq(studyId))
                .fetch();
    }

    @Override
    @Deprecated
    public Optional<StudyRule> findStudyRule(Long studyRuleId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(studyRule)
                .leftJoin(studyRule.problems, problem).fetchJoin().fetchOne());
    }


}
