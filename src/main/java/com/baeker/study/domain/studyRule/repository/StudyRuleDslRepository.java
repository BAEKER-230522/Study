package com.baeker.study.domain.studyRule.repository;

import com.baeker.study.domain.studyRule.dto.query.StudyRuleQueryDto;
import com.baeker.study.domain.studyRule.entity.StudyRule;

import java.util.List;
import java.util.Optional;

public interface StudyRuleDslRepository {
    Optional<List<StudyRule>> findStudyRuleActiveFromStudy(Long studyId);
    StudyRuleQueryDto findStudyRuleDetail(StudyRule studyRule);
    Optional<List<StudyRule>> findAllAndNotYetSendMail();

//    Optional<StudyRule> findStudyRule(Long studyRuleId);
}
