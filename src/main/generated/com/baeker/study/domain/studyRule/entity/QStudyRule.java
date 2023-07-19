package com.baeker.study.domain.studyRule.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyRule is a Querydsl query type for StudyRule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyRule extends EntityPathBase<StudyRule> {

    private static final long serialVersionUID = -1422485095L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyRule studyRule = new QStudyRule("studyRule");

    public final StringPath about = createString("about");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final DatePath<java.time.LocalDate> deadline = createDate("deadline", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Mission> mission = createEnum("mission", Mission.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final ListPath<com.baeker.study.domain.problem.Problem, com.baeker.study.domain.problem.QProblem> problems = this.<com.baeker.study.domain.problem.Problem, com.baeker.study.domain.problem.QProblem>createList("problems", com.baeker.study.domain.problem.Problem.class, com.baeker.study.domain.problem.QProblem.class, PathInits.DIRECT2);

    public final NumberPath<Long> ruleId = createNumber("ruleId", Long.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final EnumPath<Status> status = createEnum("status", Status.class);

    public final com.baeker.study.study.domain.entity.QStudy study;

    public QStudyRule(String variable) {
        this(StudyRule.class, forVariable(variable), INITS);
    }

    public QStudyRule(Path<? extends StudyRule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyRule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyRule(PathMetadata metadata, PathInits inits) {
        this(StudyRule.class, metadata, inits);
    }

    public QStudyRule(Class<? extends StudyRule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.study = inits.isInitialized("study") ? new com.baeker.study.study.domain.entity.QStudy(forProperty("study")) : null;
    }

}

