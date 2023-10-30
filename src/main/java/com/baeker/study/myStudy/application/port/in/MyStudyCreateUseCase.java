package com.baeker.study.myStudy.application.port.in;

import com.baeker.study.global.dto.reqDto.InviteReqDto;
import com.baeker.study.study.domain.entity.Study;

public interface MyStudyCreateUseCase {

    Long myStudy(Long memberId, Study study);

    Long join(Long memberId, Study study, String msg);

    Long invite(Long memberId, Study study, InviteReqDto dto);
}
