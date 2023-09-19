package com.baeker.study.study.application.port.in;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.in.resDto.CreateResDto;
import com.baeker.study.study.in.resDto.MemberResDto;

public interface StudyCreateUseCase {

    CreateResDto study(Long memberId, CreateReqDto dto);
}
