package com.baeker.study.study.in.listener;

import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.in.event.AddSolvedCountEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class StudyEventListener {

    private final StudyService studyService;

    @EventListener
    public void listen(AddSolvedCountEvent event) {
        studyService.addSolveCount(event);
    }
}
