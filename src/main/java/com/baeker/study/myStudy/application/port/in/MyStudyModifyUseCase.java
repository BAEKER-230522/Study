package com.baeker.study.myStudy.application.port.in;

import com.baeker.study.myStudy.domain.entity.MyStudy;

public interface MyStudyModifyUseCase {

    void msg(Long memberId, MyStudy myStudy, String msg);

    void accept(Long memberId, MyStudy myStudy);
}
