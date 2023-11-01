package com.baeker.study.study.application.port.in;

import com.baeker.study.global.feign.dto.CandidateResDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.legacy.in.resDto.MemberResDto;
import com.baeker.study.study.legacy.in.resDto.StudyResDto;

import java.util.List;

public interface StudyQueryUseCase {

    Study byName(String name);

    Study byId(Long id);

    List<StudyResDto> all();

    List<StudyResDto> byMemberId(Long memberId, int status);

    List<MemberResDto> byMemberList(Study study);

    CandidateResDto byCandidateList(Study study);

    List<StudyResDto> allOrderByRanking(int page, int content);

    List<StudyResDto> byInput(String input, int page, int content);
}
