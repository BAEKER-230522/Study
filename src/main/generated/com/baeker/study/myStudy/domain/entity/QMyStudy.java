package com.baeker.study.myStudy.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyStudy is a Querydsl query type for MyStudy
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyStudy extends EntityPathBase<MyStudy> {

    private static final long serialVersionUID = -320567081L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyStudy myStudy = new QMyStudy("myStudy");

    public final com.baeker.study.base.entity.QBaseEntity _super = new com.baeker.study.base.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> member = createNumber("member", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath msg = createString("msg");

    public final EnumPath<StudyStatus> status = createEnum("status", StudyStatus.class);

    public final com.baeker.study.study.domain.entity.QStudy study;

    public QMyStudy(String variable) {
        this(MyStudy.class, forVariable(variable), INITS);
    }

    public QMyStudy(Path<? extends MyStudy> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyStudy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyStudy(PathMetadata metadata, PathInits inits) {
        this(MyStudy.class, metadata, inits);
    }

    public QMyStudy(Class<? extends MyStudy> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.study = inits.isInitialized("study") ? new com.baeker.study.study.domain.entity.QStudy(forProperty("study")) : null;
    }

}

