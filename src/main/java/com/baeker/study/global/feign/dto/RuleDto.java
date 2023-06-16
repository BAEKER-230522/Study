package com.baeker.study.global.feign.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleDto {
    @Schema(description = "RuleId", example = "1")
    private Long id;
    @Schema(description = "RuleName Rule서비스의 이름", example = "이름")
    private String name;
    @Schema(description = "Rule 서비스의 소개", example = "소개")
    private String about;
    @Schema(description = "Rule 서비스의 OJ", example = "BaekJoon")
    private String provider;
    @Schema(description = "Rule 서비스의 경험치", example = "10")
    private int xp;
    @Schema(description = "Rule 서비스의 문제풀이수(명시된 만큼 문제 풀어야함)", example = "10")
    private int count;
    @Schema(description = "Rule 서비스의 난이도(명시되어있는 난이도의 문제를 풀어야함)",example = "Gold")
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
