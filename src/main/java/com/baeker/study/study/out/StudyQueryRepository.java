package com.baeker.study.study.out;

import com.baeker.study.myStudy.domain.entity.QMyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import com.baeker.study.study.domain.entity.QStudy;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.QStudyResDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.baeker.study.myStudy.domain.entity.StudyStatus.MEMBER;

@Repository
public class StudyQueryRepository {

    private final JPAQueryFactory query;
    private QStudy study = QStudy.study;
    private QMyStudy myStudy = QMyStudy.myStudy;

    public StudyQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    //-- find by member & status --//
    public List<Study> findByMember(Long member, StudyStatus status) {

        return query
                .selectFrom(study)
                .innerJoin(study.myStudies, myStudy)
                .where(myStudy.member.eq(member)
                        .and(myStudy.status.eq(status)))
                .fetch();
    }

    //-- find by member --//
    public List<Study> findByMember(Long member) {

        return query
                .selectFrom(study)
                .where(study.myStudies.any().member.eq(member))
                .fetch();
    }

    //-- find all order by ranking --//
    public List<StudyResDto> findAllOrderByRanking(int page, int content) {
        return query
                .select(new QStudyResDto(
                        study.id,
                        study.createDate,
                        study.modifyDate,
                        study.name,
                        study.about,
                        study.leader,
                        study.capacity,
                        study.xp,
                        study.bronze,
                        study.silver,
                        study.gold,
                        study.diamond,
                        study.ruby,
                        study.platinum,
                        study.studyMember,
                        study.ranking
                ))
                .from(study)
                .orderBy(nullsLast(study.ranking), study.ranking.desc())
                .offset(page * content)
                .limit(content)
                .fetch();
    }

    private <T extends Comparable> OrderSpecifier<T> nullsLast(Path<T> path) {
        return new OrderSpecifier<>(Order.ASC, path, OrderSpecifier.NullHandling.NullsLast);
    }

    //-- find by input --//
    public List<StudyResDto> findByInput(String input, int page, int content) {
        return query
                .select(new QStudyResDto(
                        study.id,
                        study.createDate,
                        study.modifyDate,
                        study.name,
                        study.about,
                        study.leader,
                        study.capacity,
                        study.xp,
                        study.bronze,
                        study.silver,
                        study.gold,
                        study.diamond,
                        study.ruby,
                        study.platinum,
                        study.studyMember,
                        study.ranking
                ))
                .from(study)
                .where(study.name.like("%" + input + "%"))
                .offset(page * content)
                .limit(content)
                .fetch();
    }

    //-- xp 순으로 정렬한 모든 member 목록 --//
    public List<Study> findStudyRanking() {
        return query
                .selectFrom(study)
                .orderBy(study.xp.desc())
                .fetch();
    }
}
