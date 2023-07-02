package com.baeker.study.domain.studyRule.repository;

import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.baeker.study.domain.studyRule.entity.QStudyRule.studyRule;
import static com.baeker.study.study.domain.entity.QStudy.study;

@Repository
@RequiredArgsConstructor
public class StudyRuleDslRepositoryImp implements StudyRuleDslRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StudyRule> getStudyRuleFromStudy(Long studyId) {
        return jpaQueryFactory.select(studyRule)
                .from(studyRule)
                .innerJoin(study)
                .on(studyRule.id.eq(study.id))
                .fetch();
    }
}
