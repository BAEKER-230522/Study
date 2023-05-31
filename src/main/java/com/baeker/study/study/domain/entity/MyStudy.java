package com.baeker.study.study.domain.entity;

import com.baeker.study.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
public class MyStudy extends BaseEntity {

    private String msg;
    private StudyStatus status;
    private Long memberId;

    @ManyToOne(fetch = LAZY)
    private Study study;
}
