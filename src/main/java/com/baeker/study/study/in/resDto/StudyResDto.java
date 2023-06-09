package com.baeker.study.study.in.resDto;

import com.baeker.study.study.domain.entity.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudyResDto {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "YYYY.MM.DD HH:mm:ss")
    private LocalDateTime createDate;
    @Schema(example = "YYYY.MM.DD HH:mm:ss")
    private LocalDateTime modifyDate;
    @Schema(example = "스터디 이름")
    private String name;
    @Schema(example = "스터디 소개")
    private String about;
    @Schema(example = "스터디 리더")
    private String leader;
    @Schema(example = "10")
    private Integer capacity;
    @Schema(example = "10")
    private Integer xp;
    @Schema(example = "1")
    int bronze;
    @Schema(example = "1")
    int silver;
    @Schema(example = "1")
    int gold;
    @Schema(example = "1")
    int diamond;
    @Schema(example = "1")
    int ruby;
    @Schema(example = "1")
    int platinum;
    @Schema(example = "6")
    int solvedCount;


    public StudyResDto(Study study) {
        this.id = study.getId();
        this.createDate = study.getCreateDate();
        this.modifyDate = study.getModifyDate();
        this.name = study.getName();
        this.about = study.getAbout();
        this.leader = study.getLeader();
        this.capacity = study.getCapacity();
        this.xp = study.getXp();
        this.bronze = study.getBronze();
        this.silver = study.getSilver();
        this.gold = study.getGold();
        this.diamond = study.getDiamond();
        this.ruby = study.getRuby();
        this.platinum = study.getPlatinum();
        this.solvedCount = study.solvedCount();
    }
}
