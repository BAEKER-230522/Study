package com.baeker.study.myStudy.legacy.out.reqDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteMyStudyReqDto {

    private Long memberId;
    private Long myStudyId;
}
