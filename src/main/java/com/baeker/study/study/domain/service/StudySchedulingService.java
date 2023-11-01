package com.baeker.study.study.domain.service;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.legacy.out.StudyQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudySchedulingService {

    private final StudyQueryRepository studyQueryRepository;

    @Transactional
    @Scheduled(cron = "0 0 20 * * *")
    public void updateRanking() {
        log.info("study ranking update 시작");

        List<Study> studyList = studyQueryRepository.findStudyRanking();

        for (int i = 0; i < studyList.size(); i++)
            studyList.get(i).updateRanking(i + 1);

        log.info("모든 study ranking update 완료");
    }
}
