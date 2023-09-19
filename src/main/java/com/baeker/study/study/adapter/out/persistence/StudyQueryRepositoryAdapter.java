package com.baeker.study.study.adapter.out.persistence;

import com.baeker.study.study.application.port.out.persistence.StudyQueryRepositoryPort;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudyQueryRepositoryAdapter implements StudyQueryRepositoryPort {

    private final JPAQueryFactory query;

    public StudyQueryRepositoryAdapter(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<StudyResDto> byMemberId(Long memberId, int status) {
        return null;
    }

    @Override
    public List<StudyResDto> allOrderByRanking(int page, int content) {
        return null;
    }

    @Override
    public List<StudyResDto> byInput(String input, int page, int content) {
        return null;
    }
}
