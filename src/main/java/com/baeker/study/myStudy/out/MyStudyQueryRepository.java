package com.baeker.study.myStudy.out;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.QMyStudy;
import com.baeker.study.study.domain.entity.Study;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MyStudyQueryRepository {

    private final JPAQueryFactory query;
    private QMyStudy myStudy = QMyStudy.myStudy;


    public MyStudyQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    //-- find by member & study --//
    public List<MyStudy> findByMemberStudy(Long member, Study study) {
        return query
                .selectFrom(myStudy)
                .where(myStudy.member.eq(member)
                        .and(myStudy.study.eq(study)))
                .fetch();
    }

}
