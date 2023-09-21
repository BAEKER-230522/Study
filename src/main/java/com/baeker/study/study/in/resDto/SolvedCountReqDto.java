package com.baeker.study.study.in.resDto;

import lombok.Data;

@Data
public class SolvedCountReqDto {
    private Long id;
    private int bronze;
    private int silver;
    private int gold;
    private int diamond;
    private int ruby;
    private int platinum;

    public SolvedCountReqDto(Long id, int bronze, int silver, int gold, int diamond, int ruby, int platinum) {
        this.id = id;
        this.bronze = bronze;
        this.silver = silver;
        this.gold = gold;
        this.diamond = diamond;
        this.ruby = ruby;
        this.platinum = platinum;
    }
}
