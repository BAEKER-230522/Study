package com.baeker.study.studyRule.event;

import com.baeker.study.global.exception.service.NotFoundException;
import com.baeker.study.studyRule.service.StudyRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StudyRuleEventListener {
    private final StudyRuleService studyRuleService;

    @EventListener
    public void listen(StudyRuleEvent event) {
        try {
//            studyRuleService.updateStudySolved(event.getStudyRuleId());
        } catch (NotFoundException e) {
            log.error(e.getMessage());
        }
    }
}
