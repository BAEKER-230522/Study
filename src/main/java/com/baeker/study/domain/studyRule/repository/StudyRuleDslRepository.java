package com.baeker.study.domain.studyRule.repository;

import com.baeker.study.domain.studyRule.entity.StudyRule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRuleDslRepository {
    List<StudyRule> getStudyRuleFromStudy(Long studyId);

}
