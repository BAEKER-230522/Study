package com.baeker.study.study.in.reqDto;

import com.baeker.study.study.in.event.AddSolvedCountEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaekjoonDto {

    private Long id;
    int bronze;
    int sliver;
    int gold;
    int diamond;
    int ruby;
    int platinum;

    public BaekjoonDto(AddSolvedCountEvent event) {
        this.id = event.getMember();
        this.bronze = event.getBronze();
        this.sliver = event.getSliver();
        this.gold = event.getGold();
        this.diamond = event.getDiamond();
        this.ruby = event.getRuby();
        this.platinum = event.getPlatinum();
    }
}
