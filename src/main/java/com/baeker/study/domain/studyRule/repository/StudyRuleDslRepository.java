package com.baeker.study.domain.studyRule.repository;

import com.baeker.study.domain.studyRule.entity.StudyRule;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyRuleDslRepository {
    List<StudyRule> findStudyRuleFromStudy(Long studyId);

    Optional<StudyRule> findStudyRule(Long studyRuleId);
}
