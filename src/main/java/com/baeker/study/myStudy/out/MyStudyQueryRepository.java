package com.baeker.study.myStudy.out;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class MyStudyQueryRepository {

    private final JPAQueryFactory query;


    public MyStudyQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    //-- find by member & study --//

}
