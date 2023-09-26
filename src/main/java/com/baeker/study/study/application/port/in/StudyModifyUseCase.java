package com.baeker.study.study.application.port.in;

import com.baeker.study.study.adapter.in.reqDto.StudyModifyResDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.AddXpReqDto;
import com.baeker.study.study.in.reqDto.UpdateLeaderReqDto;
import com.baeker.study.study.in.resDto.SolvedCountReqDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.baeker.study.study.in.resDto.UpdateResDto;

import java.util.List;

public interface StudyModifyUseCase {

    UpdateResDto info(Study study, Long memberId, StudyModifyResDto dto);

    StudyResDto leader(Study study, Long memberId, UpdateLeaderReqDto dto);

    UpdateResDto xp(Study study, AddXpReqDto dto);

    void solvedCount(SolvedCountReqDto dto);

    void ranking(List<Study> studyList);
}
