package com.baeker.study.study.legacy.in.resDto;

import com.baeker.study.study.domain.entity.StudySnapshot;
import lombok.Data;

@Data
public class SnapshotResDto {

    private Long id;
    private Long studyId;
    private String studyName;
    private String dayOfWeek;
    int bronze;
    int silver;
    int gold;
    int diamond;
    int ruby;
    int platinum;
    int solvedCount;

    public SnapshotResDto(StudySnapshot snapshot) {
        this.id = snapshot.getId();
        this.studyId = snapshot.getStudy().getId();
        this.studyName = snapshot.getStudy().getName();
        this.dayOfWeek = snapshot.getDayOfWeek();
        this.bronze = snapshot.getBronze();
        this.silver = snapshot.getSilver();
        this.gold = snapshot.getGold();
        this.diamond = snapshot.getDiamond();
        this.ruby = snapshot.getRuby();
        this.platinum = snapshot.getPlatinum();
        this.solvedCount = snapshot.solvedCount();
    }
}
