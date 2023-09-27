package com.baeker.study.study.application.port.in;

import com.baeker.study.study.domain.entity.Study;

public interface StudyDeleteUseCase {

    void study(Study study, Long memberId);
}
