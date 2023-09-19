package com.baeker.study.study.application.port.in;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.in.resDto.MemberResDto;

public interface StudyCreateUseCase {

    Study study(Long memberId, CreateReqDto dto);
}
