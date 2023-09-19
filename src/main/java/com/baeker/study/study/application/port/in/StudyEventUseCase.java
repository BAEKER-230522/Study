package com.baeker.study.study.application.port.in;

import com.baeker.study.study.in.event.AddSolvedCountEvent;

public interface StudyEventUseCase {

    void updateSolvedCount(AddSolvedCountEvent event);
}
