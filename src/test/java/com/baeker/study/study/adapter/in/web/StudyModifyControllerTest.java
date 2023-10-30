package com.baeker.study.study.adapter.in.web;

import com.baeker.study.global.dto.reqDto.StudyModifyReqDto;
import com.baeker.study.study.in.reqDto.UpdateLeaderReqDto;
import com.baeker.study.testUtil.adapter.web.StudyModifyControllerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.baeker.study.testUtil.global.integration.MockMvcRequest.patchReq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("단위 - 스터디 수정 api")
@SpringJUnitConfig
@WebMvcTest(StudyModifyController.class)
class StudyModifyControllerTest extends StudyModifyControllerMock {

    @Autowired MockMvc mvc;

    @Value("${custom.mapping.study.web_usr}")
    String mapping;

    @BeforeEach
    void setup() {
        getMemberIdMocking();
        findStudyByIdMocking();
        modifyInfoMocking();
        modifyLeaderMocking();
    }

    @Test
    @DisplayName("스터디 정보 수정")
    void no1() throws Exception {
        String memberId = "1";
        Long studyId = 1L;
        StudyModifyReqDto reqDto = infoReqDto(studyId);

        ResultActions result = patchReq(mvc,
                mapping + "/v2/info", memberId, reqDto
        );

        result
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id").value(studyId));
    }

    @Test
    @DisplayName("스터디장 변경")
    void no2() throws Exception {
        String memberId = "1";
        String newLeaderId = "2";
        Long studyId = 1L;
        UpdateLeaderReqDto reqDto = leaderReqDto(studyId, newLeaderId);


        ResultActions result = patchReq(mvc,
                mapping + "/v2/leader", memberId, reqDto
        );


        result
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("leader").value(newLeaderId));
    }


    private StudyModifyReqDto infoReqDto(Long studyId) {
        StudyModifyReqDto dto = new StudyModifyReqDto();
        dto.setStudyId(studyId);
        return dto;
    }

    private UpdateLeaderReqDto leaderReqDto(Long studyId, String newLeaderId){
        UpdateLeaderReqDto dto = new UpdateLeaderReqDto();
        dto.setStudyId(studyId);
        dto.setNewLeader(Long.valueOf(newLeaderId));
        return dto;
    }
}