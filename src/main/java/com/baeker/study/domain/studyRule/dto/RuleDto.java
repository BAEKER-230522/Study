package com.baeker.study.domain.studyRule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleDto {
    private Long id;
    private String name;
    private String about;
    private String provider;
    private int xp;
    private int count;
    private String difficulty;

    public RuleDto(RuleDto ruleDto) {
        this.id = ruleDto.getId();
        this.name = ruleDto.getName();
        this.about = ruleDto.getAbout();
        this.provider = ruleDto.getProvider();
        this.xp = ruleDto.getXp();
        this.count = ruleDto.getCount();
        this.difficulty = ruleDto.getDifficulty();
    }
}
