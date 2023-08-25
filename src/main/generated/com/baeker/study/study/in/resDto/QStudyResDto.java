package com.baeker.study.study.in.resDto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.baeker.study.study.in.resDto.QStudyResDto is a Querydsl Projection type for StudyResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QStudyResDto extends ConstructorExpression<StudyResDto> {

    private static final long serialVersionUID = -1099272279L;

    public QStudyResDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<java.time.LocalDateTime> createDate, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifyDate, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> about, com.querydsl.core.types.Expression<Long> leader, com.querydsl.core.types.Expression<Integer> capacity, com.querydsl.core.types.Expression<Integer> xp, com.querydsl.core.types.Expression<Integer> bronze, com.querydsl.core.types.Expression<Integer> silver, com.querydsl.core.types.Expression<Integer> gold, com.querydsl.core.types.Expression<Integer> diamond, com.querydsl.core.types.Expression<Integer> ruby, com.querydsl.core.types.Expression<Integer> platinum, com.querydsl.core.types.Expression<Long> studyMember) {
        super(StudyResDto.class, new Class<?>[]{long.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, String.class, String.class, long.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, long.class}, id, createDate, modifyDate, name, about, leader, capacity, xp, bronze, silver, gold, diamond, ruby, platinum, studyMember);
    }

    public QStudyResDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<java.time.LocalDateTime> createDate, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifyDate, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> about, com.querydsl.core.types.Expression<Long> leader, com.querydsl.core.types.Expression<Integer> capacity, com.querydsl.core.types.Expression<Integer> xp, com.querydsl.core.types.Expression<Integer> bronze, com.querydsl.core.types.Expression<Integer> silver, com.querydsl.core.types.Expression<Integer> gold, com.querydsl.core.types.Expression<Integer> diamond, com.querydsl.core.types.Expression<Integer> ruby, com.querydsl.core.types.Expression<Integer> platinum) {
        super(StudyResDto.class, new Class<?>[]{long.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, String.class, String.class, long.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class}, id, createDate, modifyDate, name, about, leader, capacity, xp, bronze, silver, gold, diamond, ruby, platinum);
    }

}

