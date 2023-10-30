package com.baeker.study.studyRule.repository;

import com.baeker.study.studyRule.entity.StudyRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRuleRepository extends JpaRepository<StudyRule, Long>, StudyRuleDslRepository {

    Optional<StudyRule> findByName(String name);
}