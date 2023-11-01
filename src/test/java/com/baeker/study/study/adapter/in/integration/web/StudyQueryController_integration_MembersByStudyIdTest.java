package com.baeker.study.study.adapter.in.integration.web;


import com.baeker.study.global.feign.dto.CandidateResDto;
import com.baeker.study.study.legacy.in.resDto.MemberResDto;
import com.baeker.study.testUtil.global.integration.MemberClientIntegrationMock;
import org.assertj.core.api.Assertions;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("통합 - 스터디에 가입한 회원 목록 조회 통합 테스트")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StudyQueryController_integration_MembersByStudyIdTest extends MemberClientIntegrationMock {

    @Autowired MockMvc mvc;

    @BeforeEach
    void setup() {
        baekjoonConnectCheckMocking();
        updateMyStudyMocking();
        getMemberListMocking();
    }

    @Test
    @DisplayName("정회원 목록 조회 api")
    void no1() throws Exception {
        Long study1Id = createStudy(mvc, STUDY_USR_URL, 1, jwt1).getStudyId();
        Long study2Id = createStudy(mvc, STUDY_USR_URL, 2,  jwt2).getStudyId();
        Long study3Id = createStudy(mvc, STUDY_USR_URL, 3, jwt3).getStudyId();

        addMember(jwt1, study1Id, jwt2, 2L);
        addMember(jwt1, study1Id, jwt3, 3L);
        addMember(jwt2, study2Id, jwt1, 1L);

        List<MemberResDto> study1 = requestMemberApi(study1Id);
        List<MemberResDto> study2 = requestMemberApi(study2Id);
        List<MemberResDto> study3 = requestMemberApi(study3Id);

        Assertions.assertThat(study1.size()).isEqualTo(3);
        Assertions.assertThat(study2.size()).isEqualTo(2);
        Assertions.assertThat(study3.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("가입 대기회원 목록 조회 api")
    void no2() throws Exception {
        Long study1Id = createStudy(mvc, STUDY_USR_URL, 1, jwt1).getStudyId();
        Long study2Id = createStudy(mvc, STUDY_USR_URL, 2,  jwt2).getStudyId();
        Long study3Id = createStudy(mvc, STUDY_USR_URL, 3, jwt3).getStudyId();


        joinStudy(mvc, MY_STUDY_USR_URL, study1Id, jwt2);
        inviteStudy(mvc, MY_STUDY_USR_URL, study1Id, 3L, jwt1);
        inviteStudy(mvc, MY_STUDY_USR_URL, study2Id, 1L, jwt2);

        CandidateResDto study1 =  requestCandidateApi(study1Id);
        CandidateResDto study2 =  requestCandidateApi(study2Id);
        CandidateResDto study3 =  requestCandidateApi(study3Id);


        Assertions.assertThat(study1.getPendingSize()).isEqualTo(1);
        Assertions.assertThat(study1.getInviteSize()).isEqualTo(1);

        Assertions.assertThat(study2.getPendingSize()).isEqualTo(0);
        Assertions.assertThat(study2.getInviteSize()).isEqualTo(1);

        Assertions.assertThat(study3.getPendingSize()).isEqualTo(0);
        Assertions.assertThat(study3.getInviteSize()).isEqualTo(0);
    }

    private void addMember(String leader, Long studyId, String target, Long targetId) throws Exception {
        joinStudy(mvc, MY_STUDY_USR_URL, studyId, target);
        accept(mvc, MY_STUDY_USR_URL, studyId, targetId, leader);
    }

    private List<MemberResDto> requestMemberApi(Long studyId) throws Exception {
        ResultActions result = getReq(mvc,
                STUDY_PUB_URL + "/v2/member-list/{studyId}",
                studyId);
        List<MemberResDto> resDto =  toList(result, MemberResDto.class);

        result.andExpect(status().is2xxSuccessful());
        return resDto;
    }

    private CandidateResDto requestCandidateApi(Long studyId) throws Exception{
        ResultActions result = getReq(mvc,
                STUDY_PUB_URL + "/v2/candidate-list/{studyId}",
                studyId);
        CandidateResDto resDto =  toResDto(result, CandidateResDto.class);

        result.andExpect(status().is2xxSuccessful());
        return resDto;
    }
}