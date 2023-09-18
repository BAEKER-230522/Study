package com.baeker.study.study.in.listener;

import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.in.event.AddSolvedCountEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class StudyEventListener {

    private final StudyService studyService;

    @EventListener
    public void listen(AddSolvedCountEvent event) {
        log.info("solved count update event 확인 member id = {}", event.getMember());
        studyService.addSolveCount(event);
    }
}
