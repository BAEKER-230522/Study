package com.baeker.study.study.out;

import com.baeker.study.myStudy.domain.entity.QMyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import com.baeker.study.study.domain.entity.QStudy;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.QStudyResDto;
import com.baeker.study.study.in.resDto.StudyResDto;
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

    //-- find all order by study
    public List<StudyResDto> findAllOrderByXp(int page, int content) {
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
                                study.platinum
                        ))
                .from(study)
                .orderBy(study.xp.desc())
                .offset(page * content)
                .limit(content)
                .fetch();
    }
}
