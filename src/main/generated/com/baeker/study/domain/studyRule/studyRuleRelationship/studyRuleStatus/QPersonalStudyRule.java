package com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonalStudyRule is a Querydsl query type for PersonalStudyRule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonalStudyRule extends EntityPathBase<PersonalStudyRule> {

    private static final long serialVersionUID = -227470466L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonalStudyRule personalStudyRule = new QPersonalStudyRule("personalStudyRule");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final ListPath<com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.ProblemStatus, com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.QProblemStatus> problemStatuses = this.<com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.ProblemStatus, com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.QProblemStatus>createList("problemStatuses", com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.ProblemStatus.class, com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.QProblemStatus.class, PathInits.DIRECT2);

    public final EnumPath<com.baeker.study.domain.studyRule.entity.Status> status = createEnum("status", com.baeker.study.domain.studyRule.entity.Status.class);

    public final com.baeker.study.domain.studyRule.entity.QStudyRule studyRule;

    public QPersonalStudyRule(String variable) {
        this(PersonalStudyRule.class, forVariable(variable), INITS);
    }

    public QPersonalStudyRule(Path<? extends PersonalStudyRule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonalStudyRule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonalStudyRule(PathMetadata metadata, PathInits inits) {
        this(PersonalStudyRule.class, metadata, inits);
    }

    public QPersonalStudyRule(Class<? extends PersonalStudyRule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studyRule = inits.isInitialized("studyRule") ? new com.baeker.study.domain.studyRule.entity.QStudyRule(forProperty("studyRule"), inits.get("studyRule")) : null;
    }

}

