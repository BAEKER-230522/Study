package com.baeker.study.study.in.resDto;

import com.baeker.study.study.domain.entity.Study;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
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
    private Long leader;
    @Schema(example = "10")
    private Integer capacity;
    @Schema(example = "5")
    private Integer studyMember;
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
    Integer ranking;


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
        this.studyMember = study.getMyStudies().size();
        this.ranking = study.getRanking();
    }

    @QueryProjection
    public StudyResDto(Long id, LocalDateTime createDate, LocalDateTime modifyDate, String name, String about, Long leader, Integer capacity, Integer xp, int bronze, int silver, int gold, int diamond, int ruby, int platinum, Long studyMember, Integer ranking) {
        this.id = id;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.name = name;
        this.about = about;
        this.leader = leader;
        this.capacity = capacity;
        this.studyMember = Math.toIntExact(studyMember);
        this.xp = xp;
        this.bronze = bronze;
        this.silver = silver;
        this.gold = gold;
        this.diamond = diamond;
        this.ruby = ruby;
        this.platinum = platinum;
        this.solvedCount = bronze + silver + gold + diamond + ruby + platinum;
        this.ranking = ranking;
    }

    @QueryProjection
    public StudyResDto(Long id, LocalDateTime createDate, LocalDateTime modifyDate, String name, String about, Long leader, Integer capacity, Integer xp, int bronze, int silver, int gold, int diamond, int ruby, int platinum, Integer ranking) {
        this.id = id;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.name = name;
        this.about = about;
        this.leader = leader;
        this.capacity = capacity;
        this.xp = xp;
        this.bronze = bronze;
        this.silver = silver;
        this.gold = gold;
        this.diamond = diamond;
        this.ruby = ruby;
        this.platinum = platinum;
        this.solvedCount = bronze + silver + gold + diamond + ruby + platinum;
        this.ranking = ranking;
    }
}
