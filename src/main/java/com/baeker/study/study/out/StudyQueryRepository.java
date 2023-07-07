package com.baeker.study.study.out;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.QMyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import com.baeker.study.study.domain.entity.QStudy;
import com.baeker.study.study.domain.entity.Study;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
