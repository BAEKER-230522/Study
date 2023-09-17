package com.baeker.study.study.in.reqDto;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.event.AddSolvedCountEvent;
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

    public BaekjoonDto(AddSolvedCountEvent event) {
        this.id = event.getMember();
        this.bronze = event.getBronze();
        this.silver = event.getSilver();
        this.gold = event.getGold();
        this.diamond = event.getDiamond();
        this.ruby = event.getRuby();
        this.platinum = event.getPlatinum();
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
