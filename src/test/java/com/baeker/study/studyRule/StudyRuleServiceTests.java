package com.baeker.study.studyRule;

import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.domain.studyRule.dto.ProblemNumberDto;
import com.baeker.study.domain.studyRule.dto.request.CreateStudyRuleRequest;
import com.baeker.study.domain.studyRule.dto.request.ModifyStudyRuleRequest;
import com.baeker.study.domain.studyRule.entity.Status;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.service.StudyRuleService;
import com.baeker.study.domain.studyRule.studyRuleRelationship.problem.dto.CreateProblem;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.global.feign.RuleClient;
import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.myStudy.in.reqDto.JoinMyStudyReqDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.in.resDto.MemberResDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @Autowired
    MyStudyService myStudyService;
    @MockBean
    RuleClient ruleClient;
    @MockBean
    MemberClient memberClient;

    @BeforeEach
    void setFeign() {
        when(ruleClient.getRule(any()))
                .thenReturn(new RsData<>("S-1", "msg", null));
        when(memberClient.updateMyStudy(any()))
                .thenReturn(new RsData<>("S-1", "msg", null));
        when(memberClient.deleteMyStudy(any()))
                .thenReturn(new RsData<>("S-1", "msg", null));
        when(memberClient.findById(any()))
                .thenReturn(new RsData<MemberResDto>("S-1", "성공", new MemberResDto(1L, "leader", "bk1234")));
    }


    Study createStudy(int i) {
        CreateReqDto reqDto = CreateReqDto.createStudy((long) i, "이름" + i, "소개", 10);
        return studyService.create(reqDto).getStudy();
    }

    void joinStudy(Long studyId, Long memberId) {
        JoinMyStudyReqDto dto = new JoinMyStudyReqDto(studyId, memberId, "");
        Study byId = studyService.findById(studyId);
        myStudyService.join(dto, byId);
    }

    /* 현재 진행중인 스터디 */
    CreateStudyRuleRequest setRequest(int i) {
        Study study = createStudy(1);
        for (int j = 0; j < i; j++) {
            joinStudy(study.getId(), (long) j);
        }
        CreateStudyRuleRequest cr = new CreateStudyRuleRequest();
        cr.setName("이름" + i);
        cr.setAbout("소개" + i);
        cr.setStudyId(study.getId());
        List<CreateProblem> createProblems = new ArrayList<>();
        CreateProblem createProblem = new CreateProblem("A+B", 1000);
        createProblems.add(createProblem);
        cr.setXp(10.0);
        cr.setCreateProblemList(createProblems);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        cr.setStartDate(startDate);
        cr.setDeadline(endDate);
        return cr;
    }

    CreateStudyRuleRequest pagingRequest(int i) {
        Study study = createStudy(i);
        CreateStudyRuleRequest cr = new CreateStudyRuleRequest();
        cr.setName("이름" + i);
        cr.setAbout("소개" + i);
        cr.setXp(10.0);
        cr.setStudyId(study.getId());
        List<CreateProblem> createProblems = new ArrayList<>();
        CreateProblem createProblem = new CreateProblem("A+B", 1000);
        createProblems.add(createProblem);
        cr.setCreateProblemList(createProblems);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        cr.setStartDate(startDate);
        cr.setDeadline(endDate);
        return cr;
    }

    @Test
    @DisplayName("생성 메서드")
    @Rollback
    void createTest() {
        CreateStudyRuleRequest request = setRequest(1);
        Long studyRuleId = studyRuleService.create(request);
        StudyRule studyRule = studyRuleService.getStudyRule(studyRuleId);

        assertThat(studyRule.getId()).isEqualTo(studyRuleId);
    }

    @Test
    @DisplayName("수정 메서드")
    @Rollback
    void modifyTest() {
        CreateStudyRuleRequest cr = setRequest(1);
        Long studyRuleId = studyRuleService.create(cr);
        StudyRule studyRule = studyRuleService.getStudyRule(studyRuleId);

        assertThat(studyRule.getName()).isEqualTo("이름1");
        ModifyStudyRuleRequest request = new ModifyStudyRuleRequest("수정이름", "수정소개");
        studyRuleService.modify(studyRule, request);

        assertThat(studyRule.getName()).isEqualTo("수정이름");

        Map<String, String> map = new HashMap<>();
        map.put("name", "이름수정");
        map.put("없는소개", "소개수정");

        studyRuleService.updateStudyRule(studyRuleId, map);
        assertThat(studyRule.getName()).isEqualTo("이름수정");
        assertThat(studyRule.getAbout()).isEqualTo("수정소개");
    }

    @Test
    @DisplayName("삭제 메서드")
    @Rollback
    void delete() {
        CreateStudyRuleRequest request = setRequest(1);
        Long studyRuleId = studyRuleService.create(request);
        StudyRule studyRule = studyRuleService.getStudyRule(studyRuleId);


        studyRuleService.delete(studyRule);
        String msg = "";
        try {
            StudyRule studyRule1 = studyRuleService.getStudyRule(1L);
        } catch (NotFoundException e) {
            msg = e.getMessage();
        }
        assertThat(msg).isEqualTo("아이디를 확인해주세요");
    }

    @Test
    @DisplayName("조회/페이징")
    @Rollback
    void selectPaging() {

        for (int i = 101; i <= 200; i++) {
            CreateStudyRuleRequest createStudyRuleRequest = pagingRequest(i);
            studyRuleService.create(createStudyRuleRequest);
        }
        List<StudyRule> all = studyRuleService.getAll();
        assertThat(all.size()).isEqualTo(100);
        long i = 101;
        for (StudyRule studyRule : all) {
            assertThat(studyRule.getName()).isEqualTo("이름" + i++);
        }
    }

    @Test
    @DisplayName("PersonalStudyRule 에서의 Status 가 COMPLETE 로 변경되는지 확인")
    @Rollback
    void updateProblemStatusTest() {
        CreateStudyRuleRequest request = setRequest(1);
        Long studyRuleId = studyRuleService.create(request);
        StudyRule studyRule = studyRuleService.getStudyRule(studyRuleId);
        Long studyId = studyRule.getStudy().getId();
        List<ProblemNumberDto> problemNumberDtos = new ArrayList<>();
        problemNumberDtos.add(new ProblemNumberDto("1000", "10", "1000"));

//        when(studyRuleService.sendMail(any())).thenReturn()
        studyRuleService.updateProblemStatus(studyId, 1L, problemNumberDtos);
        studyRule.getPersonalStudyRules().forEach(
                (personal) -> {
                    if (personal.getMemberId().equals(1L)) {
                        Assertions.assertEquals(Status.COMPLETE, personal.getStatus());
                    } else {
                        Assertions.assertEquals(Status.FAIL, personal.getStatus());
                    }
                }
        );
    }

    @Test
    @DisplayName("모두 성공했을때 studt xp 상승 한다.")
    void addStudyXpTest() {
        CreateStudyRuleRequest request = setRequest(1);
        Long studyRuleId = studyRuleService.create(request);
        StudyRule studyRule = studyRuleService.getStudyRule(studyRuleId);
        Long studyId = studyRule.getStudy().getId();
        List<ProblemNumberDto> problemNumberDtos = new ArrayList<>();
        problemNumberDtos.add(new ProblemNumberDto("1000", "10", "1000"));

        studyRuleService.updateProblemStatus(studyId, 1L, problemNumberDtos);
        studyRuleService.updateProblemStatus(studyId, 0L, problemNumberDtos);
        Study study = studyRule.getStudy();
        Assertions.assertEquals(10, study.getXp());
    }

    @Test
    @DisplayName("미션 실패한 List 가 없을때 NotFoundException 발생")
    void emptyList_NotFoundException() {
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> studyRuleService.getNotYetSendMail(), "아직 메일을 보낼 스터디 룰이 없습니다.");
        assertEquals("아직 메일을 보낼 스터디 룰이 없습니다.", thrown.getMessage());
    }
}
