package com.baeker.study.myStudy.in.listener;

import com.baeker.study.myStudy.domain.service.MyStudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class MyStudyEventListener {

    private final MyStudyService myStudyService;

//    @EventListener
//    public void listen() {
//
//    }
}
