package com.baeker.study.studyRule;

import com.baeker.study.domain.studyRule.dto.request.CreateStudyRuleRequest;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.repository.StudyRuleRepository;
import com.baeker.study.domain.studyRule.service.StudyRuleService;
import com.baeker.study.study.domain.service.StudyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class StudyRuleServiceTests {

    @Autowired
    private StudyRuleRepository studyRuleRepository;

    @Autowired
    private StudyRuleService studyRuleService;

    @Autowired
    private StudyService studyService;

    @Test
    @DisplayName("생성 메서드")
    void createTest() {
        CreateStudyRuleRequest request = new CreateStudyRuleRequest();
        request.setRuleId(1L);
        request.setName("이름1");
        request.setAbout("소개1");
        request.setStudyId(1L);

        Long studyRuleId = studyRuleService.create(request);
        StudyRule studyRule = studyRuleService.getStudyRule(studyRuleId);

        assertThat(studyRule.getRuleId()).isEqualTo(1L);
    }



}
