package com.baeker.study.study.domain.entity;

import com.baeker.study.global.entity.ScoreBase;
import com.baeker.study.studyRule.entity.StudyRule;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.legacy.in.resDto.SolvedCountReqDto;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
public class Study extends ScoreBase {

    private String name;
    private String about;
    private Long leader;
    private Integer capacity;
    private Integer studyMember;
    private double xp;
    private Integer ranking;

    @Builder.Default
    @OneToMany(mappedBy = "study", cascade = ALL)
    private List<MyStudy> myStudies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "study", cascade = ALL)
    private List<StudyRule> studyRules = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "study", cascade = ALL)
    private List<StudySnapshot> snapshots = new ArrayList<>();


    //-- create method --//
    public static Study createStudy(String name, String about, Integer capacity, Long memberId) {
        return builder()
                .createDate(LocalDateTime.now())
                .name(name)
                .about(about)
                .leader(memberId)
                .capacity(capacity)
                .studyMember(1)
                .xp(0)
                .build();
    }

    // test 용 //
    public static Study createStudy(Long studyId, String name, String about, Integer capacity, Long memberId) {
        return builder()
                .id(studyId)
                .createDate(LocalDateTime.now())
                .name(name)
                .about(about)
                .leader(memberId)
                .capacity(capacity)
                .studyMember(1)
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
    public Study modifyLeader(Long leader) {
        return this.toBuilder()
                .leader(leader)
                .modifyDate(LocalDateTime.now())
                .build();
    }

    // 경험치 상승 //
    public void xpUp(double addXp) {
        this.xp += addXp;
    }

    // ranking update //
    public void updateRanking(Integer ranking) {
        this.ranking = ranking;
    }

    // 백준 점수 최신화 //
    public Study updateSolvedCount(SolvedCountReqDto dto) {
        return this.toBuilder()
                .bronze(this.getBronze() + dto.getBronze())
                .silver(this.getSilver() + dto.getSilver())
                .gold(this.getGold() + dto.getGold())
                .diamond(this.getDiamond() + dto.getDiamond())
                .ruby(this.getRuby() + dto.getRuby())
                .platinum(this.getPlatinum() + dto.getPlatinum())
                .build();
    }

    // 스터디 정회원 추가 //
    public void addStudyMember() {
        this.studyMember++;
    }

    public void removeStudyMember() {
        this.studyMember--;
    }
}
