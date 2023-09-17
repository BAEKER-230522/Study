package com.baeker.study.study.in.event;

import com.baeker.study.study.in.resDto.SolvedCountReqDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AddSolvedCountEvent extends ApplicationEvent {

    private Long member;
    int bronze;
    int silver;
    int gold;
    int diamond;
    int ruby;
    int platinum;

    public AddSolvedCountEvent(Object source, Long member, int bronze, int silver, int gold, int diamond, int ruby, int platinum) {
        super(source);
        this.member = member;
        this.bronze = bronze;
        this.silver = silver;
        this.gold = gold;
        this.diamond = diamond;
        this.ruby = ruby;
        this.platinum = platinum;
    }

    public AddSolvedCountEvent(Object source, SolvedCountReqDto dto) {
        super(source);
        this.member = dto.getMemberId();
        this.bronze = dto.getBronze();
        this.silver = dto.getSilver();
        this.gold = dto.getGold();
        this.diamond = dto.getDiamond();
        this.ruby = dto.getRuby();
        this.platinum = dto.getPlatinum();
    }
}
