package com.baeker.study.study.legacy.in.reqDto;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.legacy.in.resDto.SolvedCountReqDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaekjoonDto {

    private Long id;
    int bronze;
    int silver;
    int gold;
    int diamond;
    int ruby;
    int platinum;

    public BaekjoonDto(SolvedCountReqDto dto) {
        this.id = dto.getId();
        this.bronze = dto.getBronze();
        this.silver = dto.getSilver();
        this.gold = dto.getGold();
        this.diamond = dto.getDiamond();
        this.ruby = dto.getRuby();
        this.platinum = dto.getPlatinum();
    }

    public BaekjoonDto(Study study) {
        this.bronze = study.getBronze();
        this.silver = study.getSilver();
        this.gold = study.getGold();
        this.diamond = study.getDiamond();
        this.ruby = study.getRuby();
        this.platinum = study.getPlatinum();
    }
}
