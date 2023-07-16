package com.baeker.study.studyRule;

import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.domain.studyRule.dto.request.CreateStudyRuleRequest;
import com.baeker.study.domain.studyRule.dto.request.ModifyStudyRuleRequest;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.service.StudyRuleService;
import com.baeker.study.global.feign.Feign;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.in.resDto.MemberResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class StudyRuleServiceTests {
    @Autowired
    StudyRuleService studyRuleService;
    @Autowired
    StudyService studyService;
    @MockBean
    Feign feign;
    @MockBean
    MemberClient client;

    @BeforeEach
    void setFeign() {
        when(feign.getRule(any()))
                .thenReturn(new RsData<>("S-1", "msg", null));
        when(client.updateMyStudy(any()))
                .thenReturn(new RsData<>("S-1", "msg", null));
        when(client.deleteMyStudy(any()))
                .thenReturn(new RsData<>("S-1", "msg", null));
        when(client.findById(any()))
                .thenReturn(new RsData<MemberResDto>("S-1", "성공", new MemberResDto("leader")));
    }

    Study createStudy() {
        CreateReqDto reqDto = CreateReqDto.createStudy(1L, "이름", "소개", 1);
        return studyService.create(reqDto).getStudy();
    }


    @Test
    @DisplayName("생성 메서드")
    void createTest() {
        Study study = createStudy();
        CreateStudyRuleRequest request = new CreateStudyRuleRequest();
        request.setRuleId(1L);
        request.setName("이름1");
        request.setAbout("소개1");
        request.setStudyId(study.getId());

        Long studyRuleId = studyRuleService.create(request);
        StudyRule studyRule = studyRuleService.getStudyRule(studyRuleId);

        assertThat(studyRule.getRuleId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("수정 메서드")
    void modifyTest() {
        Study study = createStudy();
        CreateStudyRuleRequest cr = new CreateStudyRuleRequest();
        cr.setRuleId(1L);
        cr.setName("이름1");
        cr.setAbout("소개1");
        cr.setStudyId(study.getId());

        Long studyRuleId = studyRuleService.create(cr);
        StudyRule studyRule = studyRuleService.getStudyRule(studyRuleId);

        assertThat(studyRule.getRuleId()).isEqualTo(1L);

        ModifyStudyRuleRequest request = new ModifyStudyRuleRequest("수정이름", "수정소개", 2L);
        studyRuleService.modify(studyRule, request);

        assertThat(studyRule.getName()).isEqualTo("수정이름");
        assertThat(studyRule.getRuleId()).isEqualTo(2L);

        Map<String ,String> map = new HashMap<>();
        map.put("name", "이름수정");
        map.put("없는소개", "소개수정");
        map.put("ruleId", "3");

        studyRuleService.updateStudyRule(studyRuleId, map);
        assertThat(studyRule.getName()).isEqualTo("이름수정");
        assertThat(studyRule.getAbout()).isEqualTo("수정소개");
        assertThat(studyRule.getRuleId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("삭제 메서드")
    void delete() {
        Study study = createStudy();
        CreateStudyRuleRequest request = new CreateStudyRuleRequest();
        request.setRuleId(1L);
        request.setName("이름1");
        request.setAbout("소개1");
        request.setStudyId(study.getId());

        Long studyRuleId = studyRuleService.create(request);
        StudyRule studyRule = studyRuleService.getStudyRule(studyRuleId);

        assertThat(studyRule.getRuleId()).isEqualTo(1L);

        studyRuleService.delete(studyRule);

        try {
            StudyRule studyRule1 = studyRuleService.getStudyRule(1L);
        } catch (NotFoundException e) {
        }
    }

    @Test
    @DisplayName("조회/페이징")
    void selectPaging() {
        Study study = createStudy();

        for (int i = 1; i <= 100; i++) {
            Long ruleId = (long) i;
            CreateStudyRuleRequest createStudyRuleRequest = new CreateStudyRuleRequest();
            createStudyRuleRequest.setName("이름"+i);
            createStudyRuleRequest.setRuleId(ruleId);
            createStudyRuleRequest.setAbout("소개"+i);
            createStudyRuleRequest.setStudyId(study.getId());
            studyRuleService.create(createStudyRuleRequest);
        }
        List<StudyRule> all = studyRuleService.getAll();
        assertThat(all.size()).isEqualTo(100);
        long i = 1;
        for (StudyRule studyRule : all) {
            assertThat(studyRule.getName()).isEqualTo("이름"+i++);
        }
    }

    @Test
    @DisplayName("스터디 룰 리스트 리턴")
    void studyRuleList() {
        //TODO: 스터디 룰 리스트 테스트 코드 작성
        studyRuleService.getStudyRuleFromStudy(1L);
        }

}
