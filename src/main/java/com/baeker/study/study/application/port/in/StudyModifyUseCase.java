package com.baeker.study.study.application.port.in;

import com.baeker.study.study.adapter.in.reqDto.StudyModifyReqDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.AddXpReqDto;
import com.baeker.study.study.in.reqDto.UpdateLeaderReqDto;
import com.baeker.study.study.in.resDto.SolvedCountReqDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.baeker.study.study.in.resDto.UpdateResDto;

import java.util.List;

public interface StudyModifyUseCase {

    UpdateResDto info(Study study, Long memberId, StudyModifyReqDto dto);

    StudyResDto leader(Study study, Long memberId, UpdateLeaderReqDto dto);

    UpdateResDto xp(Study study, double addXp);

    void solvedCount(List<StudyResDto> studyList, SolvedCountReqDto dto);

    void ranking(List<Study> studyList);
}
