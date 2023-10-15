package com.baeker.study.domain.studyRule.repository;

import com.baeker.study.domain.studyRule.dto.query.StudyRuleQueryDto;
import com.baeker.study.domain.studyRule.entity.Mission;
import com.baeker.study.domain.studyRule.entity.Status;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.ProblemStatus;
import com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.dto.ProblemStatusQueryDto;
import com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.PersonalStudyRule;
import com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.dto.PersonalStudyRuleDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.baeker.study.domain.studyRule.entity.QStudyRule.studyRule;
import static com.baeker.study.domain.studyRule.studyRuleRelationship.problem.QProblem.problem;
import static com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.QProblemStatus.problemStatus;
import static com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.QPersonalStudyRule.personalStudyRule;
import static com.baeker.study.study.domain.entity.QStudy.study;

@Repository
@RequiredArgsConstructor
public class StudyRuleDslRepositoryImpl implements StudyRuleDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<List<StudyRule>> findStudyRuleActiveFromStudy(Long studyId) {
        return Optional.of(jpaQueryFactory.selectFrom(studyRule)
                .leftJoin(studyRule.study, study)
                .fetchJoin()
                .where(study.id.eq(studyId), missionEqActive())
                .fetch());
    }

    private BooleanExpression missionEqActive() {
        return studyRule.mission.eq(Mission.ACTIVE);
    }


    @Override
    public StudyRuleQueryDto findStudyRuleDetail(StudyRule serviceStudyRule) {
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

    @Override
    public List<StudyRule> findAllAndNotYetSendMail() {
        return jpaQueryFactory.selectFrom(studyRule)
                .leftJoin(studyRule.study, study)
                .fetchJoin()
                .where(notYetSendMail())
                .fetch();
    }

    @Override
    public List<StudyRule> findStudyRuleFromStudy(Long studyId) {
        // studyId -> StudyRule 조회
        return jpaQueryFactory.selectFrom(studyRule)
                .leftJoin(studyRule.study, study)
                .fetchJoin()
                .where(study.id.eq(studyId))
                .fetch();
    }

    private BooleanExpression notYetSendMail() {
        return studyRule.mission.eq(Mission.DONE)
                .and(studyRule.sendMail.eq(false))
                .and(studyRule.status.eq(Status.FAIL));
    }


    private List<ProblemStatusQueryDto> findProblemStatus(List<Long> problemStatusIds) {
        List<ProblemStatus> problemStatuses = jpaQueryFactory.select
                        (problemStatus)
                .from(problemStatus)
                .join(problem)
                    .on(problemStatus.problem.id.eq(problem.id))
                .join(personalStudyRule)
                    .on(personalStudyRule.id.eq(problemStatus.personalStudyRule.id))
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

