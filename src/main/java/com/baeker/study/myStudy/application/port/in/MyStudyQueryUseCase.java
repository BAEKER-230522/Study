package com.baeker.study.myStudy.application.port.in;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.in.resDto.MyStudyResDto;
import com.baeker.study.study.domain.entity.Study;

public interface MyStudyQueryUseCase {

    MyStudy byId(Long myStudy);

    MyStudy byStudyIdAndMemberId(Long memberId, Study study);

    MyStudyResDto toDtoByStudyIdAndMemberId(Long memberId, Study study);
}
