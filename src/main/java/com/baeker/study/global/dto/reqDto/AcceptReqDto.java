package com.baeker.study.global.dto.reqDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptReqDto {

    private Long studyId;
    private Long targetMemberId;
}
