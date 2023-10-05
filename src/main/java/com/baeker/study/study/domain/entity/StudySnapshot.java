package com.baeker.study.study.domain.entity;

import com.baeker.study.base.entity.ScoreBase;
import com.baeker.study.study.in.reqDto.BaekjoonDto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
public class StudySnapshot extends ScoreBase {

    private String dayOfWeek;

    @ManyToOne(fetch = LAZY)
    private Study study;


    //-- create snapshot --//
    public static StudySnapshot create(Study study, BaekjoonDto dto, String dayOfWeek) {
        StudySnapshot snapshot = StudySnapshot.builder()
                .study(study)
                .dayOfWeek(dayOfWeek)
                .bronze(dto.getBronze())
                .silver(dto.getSilver())
                .gold(dto.getGold())
                .diamond(dto.getDiamond())
                .ruby(dto.getRuby())
                .platinum(dto.getPlatinum())
                .build();

        study.getSnapshots().add(snapshot);
        return snapshot;
    }

    //-- update snapshot --//
    public StudySnapshot modify(BaekjoonDto dto) {
        return this.toBuilder()
                .bronze(this.getBronze() + dto.getBronze())
                .silver(this.getSilver() + dto.getSilver())
                .gold(this.getGold() + dto.getGold())
                .diamond(this.getDiamond() + dto.getDiamond())
                .ruby(this.getRuby() + dto.getRuby())
                .platinum(this.getPlatinum() + dto.getPlatinum())
                .build();
    }

}
