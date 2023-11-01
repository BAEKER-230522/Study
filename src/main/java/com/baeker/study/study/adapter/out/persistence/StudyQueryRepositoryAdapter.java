package com.baeker.study.study.adapter.out.persistence;

import com.baeker.study.myStudy.domain.entity.QMyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import com.baeker.study.study.application.port.out.persistence.StudyQueryRepositoryPort;
import com.baeker.study.study.domain.entity.QStudy;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.QStudyResDto;
import com.baeker.study.study.legacy.in.resDto.StudyResDto;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudyQueryRepositoryAdapter implements StudyQueryRepositoryPort {

    private final JPAQueryFactory query;
    private QStudy study = QStudy.study;
    private QMyStudy myStudy = QMyStudy.myStudy;

    public StudyQueryRepositoryAdapter(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<StudyResDto> byMemberId(Long memberId, StudyStatus studyStatus) {
        return query
                .select(studyResDto())
                .from(study)
                .innerJoin(study.myStudies, myStudy)
                .where(myStudy.member.eq(memberId)
                        .and(myStudy.status.eq(studyStatus)))
                .fetch();
    }

    @Override
    public List<Long> byMemberList(Study study, StudyStatus status) {
        return query
                .select(myStudy.member)
                .from(myStudy)
                .where(myStudy.study.eq(study)
                        .and(myStudy.status.eq(status)))
                .fetch();
    }

    @Override
    public List<StudyResDto> allOrderByRanking(int page, int content) {
        return query
                .select(studyResDto())
                .from(study)
                .orderBy(nullsLast(study.ranking), study.ranking.desc())
                .offset(page * content)
                .limit(content)
                .fetch();
    }

    private <T extends Comparable> OrderSpecifier<T> nullsLast(Path<T> path) {
        return new OrderSpecifier<>(Order.ASC, path, OrderSpecifier.NullHandling.NullsLast);
    }


    @Override
    public List<StudyResDto> byInput(String input, int page, int content) {
        return query
                .select(studyResDto())
                .from(study)
                .where(study.name.like("%" + input + "%"))
                .offset(page * content)
                .limit(content)
                .fetch();
    }

    private QStudyResDto studyResDto() {
        return new QStudyResDto(
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
        );
    }
}
