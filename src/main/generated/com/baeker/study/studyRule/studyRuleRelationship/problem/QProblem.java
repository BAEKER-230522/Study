package com.baeker.study.studyRule.studyRuleRelationship.problem;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProblem is a Querydsl query type for Problem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblem extends EntityPathBase<Problem> {

    private static final long serialVersionUID = -1488583294L;

    public static final QProblem problem = new QProblem("problem");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath problemName = createString("problemName");

    public final NumberPath<Integer> problemNumber = createNumber("problemNumber", Integer.class);

    public final ListPath<com.baeker.study.studyRule.studyRuleRelationship.problemStatus.ProblemStatus, com.baeker.study.studyRule.studyRuleRelationship.problemStatus.QProblemStatus> problemStatuses = this.<com.baeker.study.studyRule.studyRuleRelationship.problemStatus.ProblemStatus, com.baeker.study.studyRule.studyRuleRelationship.problemStatus.QProblemStatus>createList("problemStatuses", com.baeker.study.studyRule.studyRuleRelationship.problemStatus.ProblemStatus.class, com.baeker.study.studyRule.studyRuleRelationship.problemStatus.QProblemStatus.class, PathInits.DIRECT2);

    public QProblem(String variable) {
        super(Problem.class, forVariable(variable));
    }

    public QProblem(Path<? extends Problem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProblem(PathMetadata metadata) {
        super(Problem.class, metadata);
    }

}

