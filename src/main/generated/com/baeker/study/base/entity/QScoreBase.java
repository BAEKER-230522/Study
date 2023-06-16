package com.baeker.study.base.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QScoreBase is a Querydsl query type for ScoreBase
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QScoreBase extends EntityPathBase<ScoreBase> {

    private static final long serialVersionUID = -758622367L;

    public static final QScoreBase scoreBase = new QScoreBase("scoreBase");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> bronze = createNumber("bronze", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Integer> diamond = createNumber("diamond", Integer.class);

    public final NumberPath<Integer> gold = createNumber("gold", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final NumberPath<Integer> platinum = createNumber("platinum", Integer.class);

    public final NumberPath<Integer> ruby = createNumber("ruby", Integer.class);

    public final NumberPath<Integer> sliver = createNumber("sliver", Integer.class);

    public QScoreBase(String variable) {
        super(ScoreBase.class, forVariable(variable));
    }

    public QScoreBase(Path<? extends ScoreBase> path) {
        super(path.getType(), path.getMetadata());
    }

    public QScoreBase(PathMetadata metadata) {
        super(ScoreBase.class, metadata);
    }

}

