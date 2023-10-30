package com.baeker.study.study.adapter.in.integration.web;


import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.in.resDto.StudyResDto;
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

import java.util.List;

import static com.baeker.study.testUtil.global.integration.TestApiUtil.*;
import static com.baeker.study.testUtil.global.integration.MockMvcRequest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("통합 - 특정 필드로 study 조회")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StudyQueryController_integration_byColumnTest extends MemberClientIntegrationMock {

    @Autowired MockMvc mvc;
    @Autowired StudyQueryUseCase studyQueryUseCase;

    @BeforeEach
    void setup() {
        baekjoonConnectCheckMocking();
        updateMyStudyMocking();
        getMemberListMocking();
    }

    @Test
    @DisplayName("study id 로 조회")
    void no1() throws Exception {
        Long studyId = createStudy(mvc, STUDY_USR_URL, 1, jwt1).getStudyId();


        ResultActions result = getReq(mvc, STUDY_PUB_URL + "/v2/id/{studyId}", studyId);
        StudyResDto resDto = toResDto(result, StudyResDto.class);


        result.andExpect(status().is2xxSuccessful());
        assertThat(resDto.getName()).isEqualTo("study1");
        assertThat(resDto.getCapacity()).isEqualTo(10);
        assertThat(resDto.getLeader()).isEqualTo(1L);
    }

    @Test
    @DisplayName("study name 으로 조회")
    void no2() throws Exception {
        createStudy(mvc, STUDY_USR_URL, 1, jwt1);


        ResultActions result = getReq(mvc, STUDY_PUB_URL + "/v2/name/{name}", "study1");
        StudyResDto resDto = toResDto(result, StudyResDto.class);


        result.andExpect(status().is2xxSuccessful());
        assertThat(resDto.getName()).isEqualTo("study1");
        assertThat(resDto.getCapacity()).isEqualTo(10);
        assertThat(resDto.getLeader()).isEqualTo(1L);
    }

    @Test
    @DisplayName("가입중인 스터디 목록 조회")
    void no3() throws Exception {
        Long studyId1 = createStudy(mvc, STUDY_USR_URL, 1, jwt1).getStudyId();
        Long studyId2 = createStudy(mvc, STUDY_USR_URL, 2, jwt2).getStudyId();
        Long studyId3 = createStudy(mvc, STUDY_USR_URL, 3, jwt3).getStudyId();
        joinStudy(mvc, MY_STUDY_USR_URL, studyId2, jwt1);
        inviteStudy(mvc, MY_STUDY_USR_URL, studyId3, 1L, jwt3);


        List<StudyResDto> myStudyList = findMyStudies(1, 1L);
        List<StudyResDto> myPendingList = findMyStudies(2, 1L);
        List<StudyResDto> myInviteList = findMyStudies(3, 1L);

        assertThat(myStudyList.size()).isEqualTo(1);
        assertThat(myPendingList.size()).isEqualTo(1);
        assertThat(myInviteList.size()).isEqualTo(1);
    }



    private List<StudyResDto> findMyStudies(int status, Long memberId) throws Exception {
        ResultActions result = mvc
                .perform(get(STUDY_PUB_URL + "/v2/study-list/{memberId}", memberId)
                .contentType(APPLICATION_JSON)
                .param("status", String.valueOf(status)))
                .andDo(print());

        result.andExpect(status().is2xxSuccessful());
        return toList(result, StudyResDto.class);
    }
}