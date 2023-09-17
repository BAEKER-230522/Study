package com.baeker.study.study.in.resDto;

import lombok.Data;

@Data
public class SolvedCountReqDto {
    private Long memberId;
    private int bronze;
    private int silver;
    private int gold;
    private int diamond;
    private int ruby;
    private int platinum;
}
