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

    public final com.baeker.study.global.entity.QScoreBase _super = new com.baeker.study.global.entity.QScoreBase(this);

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

    public final NumberPath<Long> leader = createNumber("leader", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final ListPath<com.baeker.study.myStudy.domain.entity.MyStudy, com.baeker.study.myStudy.domain.entity.QMyStudy> myStudies = this.<com.baeker.study.myStudy.domain.entity.MyStudy, com.baeker.study.myStudy.domain.entity.QMyStudy>createList("myStudies", com.baeker.study.myStudy.domain.entity.MyStudy.class, com.baeker.study.myStudy.domain.entity.QMyStudy.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    //inherited
    public final NumberPath<Integer> platinum = _super.platinum;

    public final NumberPath<Integer> ranking = createNumber("ranking", Integer.class);

    //inherited
    public final NumberPath<Integer> ruby = _super.ruby;

    //inherited
    public final NumberPath<Integer> silver = _super.silver;

    public final ListPath<StudySnapshot, QStudySnapshot> snapshots = this.<StudySnapshot, QStudySnapshot>createList("snapshots", StudySnapshot.class, QStudySnapshot.class, PathInits.DIRECT2);

    public final NumberPath<Integer> studyMember = createNumber("studyMember", Integer.class);

    public final ListPath<com.baeker.study.studyRule.entity.StudyRule, com.baeker.study.studyRule.entity.QStudyRule> studyRules = this.<com.baeker.study.studyRule.entity.StudyRule, com.baeker.study.studyRule.entity.QStudyRule>createList("studyRules", com.baeker.study.studyRule.entity.StudyRule.class, com.baeker.study.studyRule.entity.QStudyRule.class, PathInits.DIRECT2);

    public final NumberPath<Double> xp = createNumber("xp", Double.class);

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

