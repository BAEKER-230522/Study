package com.baeker.study.myStudy.adapter.out.persistence;

import com.baeker.study.myStudy.application.port.out.persistence.MyStudyQueryRepositoryPort;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.QMyStudy;
import com.baeker.study.study.domain.entity.Study;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class MyStudyQueryRepositoryAdapter implements MyStudyQueryRepositoryPort {

    private final JPAQueryFactory query;
    private QMyStudy myStudy = QMyStudy.myStudy;

    public MyStudyQueryRepositoryAdapter(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public MyStudy byStudyIdAndMemberId(Long memberId, Study study) {
        return query
                .selectFrom(myStudy)
                .where(myStudy.member.eq(memberId)
                        .and(myStudy.study.eq(study)))
                .fetchFirst();
    }
}
