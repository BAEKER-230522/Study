package com.baeker.study.study.application.port.in;

import com.baeker.study.global.dto.reqDto.StudyCreateReqDto;
import com.baeker.study.study.in.resDto.CreateResDto;

public interface StudyCreateUseCase {

    CreateResDto study(Long memberId, StudyCreateReqDto dto);
}
