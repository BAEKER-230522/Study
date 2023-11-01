package com.baeker.study.study.application.port.out.persistence;

import com.baeker.study.myStudy.domain.entity.StudyStatus;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.legacy.in.resDto.StudyResDto;

import java.util.List;

public interface StudyQueryRepositoryPort {

    List<StudyResDto> byMemberId(Long memberId, StudyStatus studyStatus);

    List<Long> byMemberList(Study study, StudyStatus member);

    List<StudyResDto> allOrderByRanking(int page, int content);

    List<StudyResDto> byInput(String input, int page, int content);
}
