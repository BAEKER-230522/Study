package com.baeker.study.domain.studyRule.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * param
 * StudyRuleId
 */
@Getter
public class StudyRuleEvent extends ApplicationEvent {

    private final Long id;

    public StudyRuleEvent(Object source, Long id) {
        super(source);
        this.id = id;
    }
}