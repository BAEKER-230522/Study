package com.baeker.study.studyRule.studyRuleRelationship.problemStatus;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProblemStatus is a Querydsl query type for ProblemStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblemStatus extends EntityPathBase<ProblemStatus> {

    private static final long serialVersionUID = 1385309158L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProblemStatus problemStatus = new QProblemStatus("problemStatus");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> memory = createNumber("memory", Integer.class);

    public final com.baeker.study.studyRule.studyRuleRelationship.studyRuleStatus.QPersonalStudyRule personalStudyRule;

    public final com.baeker.study.studyRule.studyRuleRelationship.problem.QProblem problem;

    public final EnumPath<com.baeker.study.studyRule.entity.Status> status = createEnum("status", com.baeker.study.studyRule.entity.Status.class);

    public final NumberPath<Integer> time = createNumber("time", Integer.class);

    public QProblemStatus(String variable) {
        this(ProblemStatus.class, forVariable(variable), INITS);
    }

    public QProblemStatus(Path<? extends ProblemStatus> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProblemStatus(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProblemStatus(PathMetadata metadata, PathInits inits) {
        this(ProblemStatus.class, metadata, inits);
    }

    public QProblemStatus(Class<? extends ProblemStatus> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.personalStudyRule = inits.isInitialized("personalStudyRule") ? new com.baeker.study.studyRule.studyRuleRelationship.studyRuleStatus.QPersonalStudyRule(forProperty("personalStudyRule"), inits.get("personalStudyRule")) : null;
        this.problem = inits.isInitialized("problem") ? new com.baeker.study.studyRule.studyRuleRelationship.problem.QProblem(forProperty("problem")) : null;
    }

}

