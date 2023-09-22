package com.baeker.study.study.in.listener;

import com.baeker.study.study.domain.service.StudyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class StudyEventListener {

    private final StudyService studyService;
}
