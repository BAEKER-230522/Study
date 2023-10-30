package com.baeker.study.study.adapter.in.integration.api;

import com.baeker.study.testUtil.global.integration.MemberClientIntegrationMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.baeker.study.testUtil.global.integration.MockMvcRequest.getReq;
import static com.baeker.study.testUtil.global.integration.TestApiUtil.createStudy;
import static com.baeker.study.testUtil.global.integration.TestApiUtil.joinStudy;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("통합 - 스터디 가입 여부 검증")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StudyQueryApiController_isMemberTest extends MemberClientIntegrationMock {

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void setup() {
        baekjoonConnectCheckMocking();
        getMemberListMocking();
        updateMyStudyMocking();
    }


    @Test
    @DisplayName("정회원일 경우 조회")
    void no1() throws Exception {
        Long
                memberId = 1L,
                studyId = createStudy(mvc, STUDY_USR_URL, 1, jwt1).getStudyId();


        ResultActions result = getReq(mvc, STUDY_API_URL +
                "/v1/member/{memberId}/{studyId}", memberId, studyId);


        result.andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("가입된 회원이 아닐 경우")
    void no2() throws Exception {
        Long
                memberId = 2L,
                studyId = createStudy(mvc, STUDY_USR_URL, 1, jwt1).getStudyId();


        ResultActions result = getReq(mvc, STUDY_API_URL +
                "/v1/member/{memberId}/{studyId}", memberId, studyId);


        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorMsg").value("스터디에 존재하지 않는 회원"));
    }

    @Test
    @DisplayName("정회원이 아닐 경우")
    void no3() throws Exception {
        Long
                memberId = 2L,
                studyId = createStudy(mvc, STUDY_USR_URL, 1, jwt1).getStudyId();
        joinStudy(mvc, MY_STUDY_USR_URL, studyId, jwt2);


        ResultActions result = getReq(mvc, STUDY_API_URL +
                "/v1/member/{memberId}/{studyId}", memberId, studyId);


        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorMsg").value("스터디에 존재하지 않는 회원"));
    }
}