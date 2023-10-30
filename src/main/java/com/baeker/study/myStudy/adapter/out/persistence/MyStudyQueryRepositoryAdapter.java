package com.baeker.study.myStudy.adapter.out.persistence;

import com.baeker.study.myStudy.application.port.out.persistence.MyStudyQueryRepositoryPort;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.QMyStudy;
import com.baeker.study.study.domain.entity.QStudy;
import com.baeker.study.study.domain.entity.Study;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MyStudyQueryRepositoryAdapter implements MyStudyQueryRepositoryPort {

    private final JPAQueryFactory query;
    private QMyStudy ms = QMyStudy.myStudy;
    private QStudy s = QStudy.study;

    public MyStudyQueryRepositoryAdapter(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public MyStudy byStudyIdAndMemberId(Long memberId, Study study) {
        return query
                .selectFrom(ms)
                .where(ms.member.eq(memberId)
                        .and(ms.study.eq(study)))
                .fetchFirst();
    }

    @Override
    public List<MyStudy> byMemberId(Long studyId, Long memberId) {
        return query
                .selectFrom(ms)
                .join(ms.study, s)
                .where(ms.member.eq(memberId)
                        .and(s.id.eq(studyId)))
                .fetch();
    }
}
