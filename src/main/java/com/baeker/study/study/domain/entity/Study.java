package com.baeker.study.study.domain.entity;

import com.baeker.study.base.entity.ScoreBase;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.in.event.AddSolvedCountEvent;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
public class Study extends ScoreBase {

    private String name;
    private String about;
    private String leader;
    private Integer capacity;
    private Integer xp;

    @Builder.Default
    @OneToMany(mappedBy = "study")
    private List<MyStudy> myStudies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "study")
    private List<StudyRule> studyRules = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "study")
    private List<StudySnapshot> snapshots = new ArrayList<>();


    //-- create method --//
    public static Study createStudy(String name, String about, Integer capacity, String nickname) {
        return builder()
                .name(name)
                .about(about)
                .leader(nickname)
                .capacity(capacity)
                .xp(0)
                .build();
    }


    //-- business logic --//

    // 이름, 소개, 최대 인원 변경 //
    public Study modifyStudy(String name, String about, Integer capacity) {
        return this.toBuilder()
                .name(name)
                .about(about)
                .capacity(capacity)
                .modifyDate(LocalDateTime.now())
                .build();
    }

    // 리더 변경 //
    public Study modifyLeader(String leader) {
        return this.toBuilder()
                .leader(leader)
                .modifyDate(LocalDateTime.now())
                .build();
    }

    // 경험치 상승 //
    public void xpUp(Integer addXp) {
        this.xp += addXp;
    }

    // 백준 점수 최신화 //
    public Study updateSolvedCount(AddSolvedCountEvent event) {
        return this.toBuilder()
                .bronze(this.getBronze() + event.getBronze())
                .silver(this.getSilver() + event.getSilver())
                .gold(this.getGold() + event.getGold())
                .diamond(this.getDiamond() + event.getDiamond())
                .ruby(this.getRuby() + event.getRuby())
                .platinum(this.getPlatinum() + event.getPlatinum())
                .build();
    }
}
