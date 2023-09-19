package com.baeker.study.study.application.port.out.persistence;

import com.baeker.study.study.in.resDto.StudyResDto;

import java.util.List;

public interface StudyQueryRepositoryPort {

    List<StudyResDto> byMemberId(Long memberId, int status);

    List<StudyResDto> allOrderByRanking(int page, int content);

    List<StudyResDto> byInput(String input, int page, int content);
}
