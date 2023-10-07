package com.baeker.study.study.adapter.in.integration;


import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.in.resDto.MemberResDto;
import com.baeker.study.testUtil.global.integration.MemberClientIntegrationMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.baeker.study.testUtil.global.integration.CreateRow.*;
import static com.baeker.study.testUtil.global.integration.MockMvcRequest.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("스터디에 가입한 회원 목록 조회 통합 테스트")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StudyQueryController_integration_MembersByStudyIdTest extends MemberClientIntegrationMock {

    @Autowired MockMvc mvc;
    @Autowired StudyQueryUseCase studyQueryUseCase;

    @Value("${custom.mapping.study.web}")
    String mapping;

    @Value("${custom.jwt.test1}")
    String jwt1;

    @Value("${custom.jwt.test2}")
    String jwt2;

    @Value("${custom.jwt.test3}")
    String jwt3;

    @BeforeEach
    void setup() {
        baekjoonConnectCheckMocking();
        updateMyStudyMocking();
        getMemberListMocking();
    }

    @Test
    @DisplayName("정회원 목록 조회 api")
    void no1() throws Exception {
        Long study1Id = createStudy(mvc, 1, 10, jwt1).getStudyId();
        Long study2Id = createStudy(mvc, 2, 10, jwt2).getStudyId();
        Long study3Id = createStudy(mvc, 3, 10, jwt3).getStudyId();

        addMember(jwt1, study1Id, jwt2, 2L);
        addMember(jwt1, study1Id, jwt3, 3L);
        addMember(jwt2, study2Id, jwt1, 1L);

        List<MemberResDto> study1 = requestApi(study1Id);
        List<MemberResDto> study2 = requestApi(study2Id);
        List<MemberResDto> study3 = requestApi(study3Id);

        Assertions.assertThat(study1.size()).isEqualTo(3);
        Assertions.assertThat(study2.size()).isEqualTo(2);
        Assertions.assertThat(study3.size()).isEqualTo(1);
    }



    private void addMember(String leader, Long studyId, String target, Long targetId) throws Exception {
        joinStudy(mvc, studyId, target);
        accept(mvc, studyId, targetId, leader);
    }

    private List<MemberResDto> requestApi(Long studyId) throws Exception {
        ResultActions result = getReq(mvc,
                mapping + "/v2/member-list/{studyId}",
                studyId);
        List<MemberResDto> resDto =  toList(result, MemberResDto.class);

        result.andExpect(status().is2xxSuccessful());
        return resDto;
    }
}