package com.baeker.study.study.application.port.in;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.legacy.in.reqDto.BaekjoonDto;
import com.baeker.study.study.legacy.in.resDto.SnapshotResDto;

import java.util.List;

public interface SnapshotUseCase {

    void updateSnapshot(Study study, BaekjoonDto dto, int addDate);

    void createSnapshot(Study study, BaekjoonDto dto, int addDate);

    List<SnapshotResDto> getSnapshotOfWeek(Study study);

    void deleteStudy(Study study);
}
