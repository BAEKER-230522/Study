package com.baeker.study.base.domain.studyRule.repository;

import com.baeker.study.base.domain.studyRule.entity.StudyRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRuleRepository extends JpaRepository<StudyRule, Long> {
    Optional<StudyRule> findById(Long id);

    Optional<StudyRule> findByName(String name);

}

