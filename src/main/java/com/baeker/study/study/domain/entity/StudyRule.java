package com.baeker.study.study.domain.entity;

import com.baeker.study.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
public class StudyRule extends BaseEntity {
}
