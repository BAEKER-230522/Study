package com.baeker.study.base.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class ScoreBase extends BaseEntity {

    int bronze;
    int silver;
    int gold;
    int diamond;
    int ruby;
    int platinum;

    public int solvedCount() {
        return bronze + silver + gold + diamond + ruby + platinum;

    }
}
