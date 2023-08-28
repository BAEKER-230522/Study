package com.baeker.study.domain.studyRule.repository;

import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.domain.studyRule.dto.query.StudyRuleQueryDto;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.ProblemStatus;
import com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.dto.ProblemStatusQueryDto;
import com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.PersonalStudyRule;
import com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.dto.PersonalStudyRuleDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.baeker.study.base.exception.ErrorResponse.NOT_FOUND_STUDY_RULE;
import static com.baeker.study.domain.studyRule.entity.QStudyRule.studyRule;
import static com.baeker.study.domain.studyRule.studyRuleRelationship.problem.QProblem.problem;
import static com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.QProblemStatus.problemStatus;
import static com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.QPersonalStudyRule.personalStudyRule;
import static com.baeker.study.study.domain.entity.QStudy.study;

@Repository
@RequiredArgsConstructor
public class StudyRuleDslRepositoryImp implements StudyRuleDslRepository {

    private final StudyRuleRepository studyRuleRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StudyRule> findStudyRuleFromStudy(Long studyId) {
        // studyId -> StudyRule 조회
        return jpaQueryFactory.selectFrom(studyRule)
                .leftJoin(studyRule.study, study)
//                .leftJoin(studyRule.personalStudyRules, personalStudyRule)
                .fetchJoin()
                .where(study.id.eq(studyId))
                .fetch();
    }

    /**
     * StudyRule Id 로 전부 조회
     *
     * @param studyRuleId
     * @return
     */
    @Override
    public StudyRuleQueryDto findStudyRule(Long studyRuleId) {
        StudyRule serviceStudyRule = studyRuleRepository.findById(studyRuleId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_STUDY_RULE.getErrorMsg()));
        List<Long> problemIds = new ArrayList<>();
        serviceStudyRule.getPersonalStudyRules().forEach(
                personal -> personal.getProblemStatuses().forEach(
                        o -> problemIds.add(o.getId())
                )
        );

        List<ProblemStatusQueryDto> problemStatuses = findProblemStatus(problemIds);

        List<PersonalStudyRule> personalStudyRules = serviceStudyRule.getPersonalStudyRules();
        List<PersonalStudyRuleDto> personalStudyRuleDtos = new ArrayList<>();
        for (PersonalStudyRule personal : personalStudyRules) {
            List<ProblemStatusQueryDto> queryDtos = new ArrayList<>();
            for (ProblemStatusQueryDto status : problemStatuses) {
                if (personal.getMemberId().equals(status.memberId())) {
                    queryDtos.add(status);
                }
            }
            personalStudyRuleDtos.add(new PersonalStudyRuleDto(personal.getMemberId(), personal.getStatus(), queryDtos));
        }
        return new StudyRuleQueryDto(serviceStudyRule, personalStudyRuleDtos);
    }



    private List<ProblemStatusQueryDto> findProblemStatus(List<Long> problemStatusIds) {
        List<ProblemStatus> problemStatuses = jpaQueryFactory.select
                        (problemStatus)
//                        (problemStatus.personalStudyRule.id,
//                                problemStatus.problem.problemNumber,
//                                problemStatus.status)
                .from(problemStatus)
                .join(problem)
                .join(personalStudyRule)
                .fetchJoin()
                .where(problemStatus.id.in(problemStatusIds))
                .fetch();
        return problemStatuses.stream()
                        .map(p -> new ProblemStatusQueryDto(
                                p.getPersonalStudyRule().getMemberId(),
                                p.getProblem().getProblemNumber(),
                                p.getProblem().getProblemName(),
                                p.getStatus(),
                                p.getTime(),
                                p.getMemory()))
                .toList();
    }

}

//    @Override
//    @Deprecated
//    public Optional<StudyRule> findStudyRule(Long studyRuleId) {
//        return Optional.ofNullable(jpaQueryFactory.selectFrom(studyRule)
//                .leftJoin(studyRule.problems, problem).fetchJoin().fetchOne());
//    }



