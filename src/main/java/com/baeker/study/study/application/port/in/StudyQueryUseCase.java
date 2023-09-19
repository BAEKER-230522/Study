package com.baeker.study.study.application.port.in;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.in.resDto.StudyResDto;

import java.util.List;

public interface StudyQueryUseCase {

    Study byName(String name);

    Study byId(Long id);

    List<Study> all();

    List<StudyResDto> byMemberId(Long memberId, int status);

    List<StudyResDto> allOrderByRanking(int page, int content);

    List<StudyResDto> byInput(String input, int page, int content);

    List<StudySnapshot> snapshotByStudy(Study study);
}
