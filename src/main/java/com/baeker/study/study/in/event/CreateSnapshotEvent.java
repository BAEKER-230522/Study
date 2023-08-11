package com.baeker.study.study.in.event;

import com.baeker.study.study.in.resDto.MemberResDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateSnapshotEvent extends ApplicationEvent {

    private Long id;
    int bronze;
    int silver;
    int gold;
    int diamond;
    int ruby;
    int platinum;

    public CreateSnapshotEvent(Object source, Long id, MemberResDto dto) {
        super(source);
        this.id = id;
        this.bronze = dto.getBronze();
        this.silver = dto.getSilver();
        this.gold = dto.getGold();
        this.diamond = dto.getDiamond();
        this.ruby = dto.getRuby();
        this.platinum = dto.getPlatinum();
    }
}
