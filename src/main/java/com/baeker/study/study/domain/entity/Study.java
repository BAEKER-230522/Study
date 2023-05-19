package com.baeker.study.study.domain.entity;

import com.baeker.study.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
public class Study extends BaseEntity {

    private String name;
    private String about;
    private String leader;
    private Integer capacity;
    private Integer xp;

}
