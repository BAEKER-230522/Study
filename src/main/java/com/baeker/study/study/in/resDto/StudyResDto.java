package com.baeker.study.study.in.resDto;

import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.domain.entity.Study;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class StudyResDto {

    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String name;
    private String about;
    private String leader;
    private Integer capacity;
    private Integer xp;
    int bronze;
    int sliver;
    int gold;
    int diamond;
    int ruby;
    int platinum;
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
        this.sliver = study.getSliver();
        this.gold = study.getGold();
        this.diamond = study.getDiamond();
        this.ruby = study.getRuby();
        this.platinum = study.getPlatinum();
        this.solvedCount = study.solvedBaekJoon();
    }
}
