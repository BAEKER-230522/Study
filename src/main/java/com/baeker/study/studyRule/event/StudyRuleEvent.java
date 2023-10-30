package com.baeker.study.studyRule.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * param
 * memberId
 */
@Getter
public class StudyRuleEvent extends ApplicationEvent {

    private final Long studyRuleId;

    public StudyRuleEvent(Object source, Long studyRuleId) {
        super(source);
        this.studyRuleId = studyRuleId;
    }

}