package com.baeker.study.study.application.port.in;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.BaekjoonDto;

public interface SnapshotUseCase {

    void updateSnapshot(Study study, BaekjoonDto dto, String today);

    void createSnapshot(Study study, BaekjoonDto dto, String today);


}
