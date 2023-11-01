package com.baeker.study.study.legacy.out;

import com.baeker.study.study.domain.entity.QStudy;
import com.baeker.study.study.domain.entity.QStudySnapshot;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SnapshotQueryRepository {

    private final JPAQueryFactory query;
    private QStudy study = QStudy.study;
    private QStudySnapshot snapshot = QStudySnapshot.studySnapshot;

    public SnapshotQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    //-- find all by study  / 삭제 예정--//
    public List<StudySnapshot> findAllByStudy(Study study) {
        return query
                .selectFrom(snapshot)
                .where(snapshot.study.eq(study))
                .fetch();
    }
}
