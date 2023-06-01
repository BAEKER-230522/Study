package com.baeker.study.study.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudy is a Querydsl query type for Study
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudy extends EntityPathBase<Study> {

    private static final long serialVersionUID = -140287761L;

    public static final QStudy study = new QStudy("study");

    public final com.baeker.study.base.entity.QScoreBase _super = new com.baeker.study.base.entity.QScoreBase(this);

    public final StringPath about = createString("about");

    //inherited
    public final NumberPath<Integer> bronze = _super.bronze;

    public final NumberPath<Integer> capacity = createNumber("capacity", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Integer> diamond = _super.diamond;

    //inherited
    public final NumberPath<Integer> gold = _super.gold;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath leader = createString("leader");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final ListPath<com.baeker.study.myStudy.domain.entity.MyStudy, com.baeker.study.myStudy.domain.entity.QMyStudy> myStudies = this.<com.baeker.study.myStudy.domain.entity.MyStudy, com.baeker.study.myStudy.domain.entity.QMyStudy>createList("myStudies", com.baeker.study.myStudy.domain.entity.MyStudy.class, com.baeker.study.myStudy.domain.entity.QMyStudy.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    //inherited
    public final NumberPath<Integer> platinum = _super.platinum;

    //inherited
    public final NumberPath<Integer> ruby = _super.ruby;

    //inherited
    public final NumberPath<Integer> sliver = _super.sliver;

    public final ListPath<com.baeker.study.domain.studyRule.entity.StudyRule, com.baeker.study.domain.studyRule.entity.QStudyRule> studyRules = this.<com.baeker.study.domain.studyRule.entity.StudyRule, com.baeker.study.domain.studyRule.entity.QStudyRule>createList("studyRules", com.baeker.study.domain.studyRule.entity.StudyRule.class, com.baeker.study.domain.studyRule.entity.QStudyRule.class, PathInits.DIRECT2);

    public final NumberPath<Integer> xp = createNumber("xp", Integer.class);

    public QStudy(String variable) {
        super(Study.class, forVariable(variable));
    }

    public QStudy(Path<? extends Study> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudy(PathMetadata metadata) {
        super(Study.class, metadata);
    }

}

