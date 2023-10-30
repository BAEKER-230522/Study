package com.baeker.study.study.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudySnapshot is a Querydsl query type for StudySnapshot
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudySnapshot extends EntityPathBase<StudySnapshot> {

    private static final long serialVersionUID = -822330413L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudySnapshot studySnapshot = new QStudySnapshot("studySnapshot");

    public final com.baeker.study.global.entity.QScoreBase _super = new com.baeker.study.global.entity.QScoreBase(this);

    //inherited
    public final NumberPath<Integer> bronze = _super.bronze;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath dayOfWeek = createString("dayOfWeek");

    //inherited
    public final NumberPath<Integer> diamond = _super.diamond;

    //inherited
    public final NumberPath<Integer> gold = _super.gold;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    //inherited
    public final NumberPath<Integer> platinum = _super.platinum;

    //inherited
    public final NumberPath<Integer> ruby = _super.ruby;

    //inherited
    public final NumberPath<Integer> silver = _super.silver;

    public final QStudy study;

    public QStudySnapshot(String variable) {
        this(StudySnapshot.class, forVariable(variable), INITS);
    }

    public QStudySnapshot(Path<? extends StudySnapshot> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudySnapshot(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudySnapshot(PathMetadata metadata, PathInits inits) {
        this(StudySnapshot.class, metadata, inits);
    }

    public QStudySnapshot(Class<? extends StudySnapshot> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.study = inits.isInitialized("study") ? new QStudy(forProperty("study")) : null;
    }

}

