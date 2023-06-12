package com.baeker.study.domain.studyRule.event;

import com.baeker.study.domain.studyRule.service.StudyRuleService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class StudyRuleEventListener {
    private final StudyRuleService studyRuleService;

    @EventListener
    public void listen(StudyRuleEvent event) throws ParseException {
        studyRuleService.whenstudyEventType(event.getId());
    }
}
