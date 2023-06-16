package com.baeker.study.study.out;

import com.baeker.study.study.domain.entity.QStudy;
import com.baeker.study.study.domain.entity.Study;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudyQueryRepository {

    private final JPAQueryFactory query;
    private QStudy study = QStudy.study;

    public StudyQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    //-- find by member --//
    public List<Study> findByMember(Long member) {

        return query
                .selectFrom(study)
                .where(study.myStudies.any().member.eq(member))
                .fetch();
    }
}
