package com.baeker.study.study.adapter.in.web;

import com.baeker.study.global.dto.reqDto.StudyCreateReqDto;
import com.baeker.study.testUtil.adapter.web.StudyCreateControllerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.baeker.study.testUtil.global.integration.MockMvcRequest.postReq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("단위 - 스터디 생성 api 테스트")
@SpringJUnitConfig
@WebMvcTest(StudyCreateController.class)
class StudyCreateControllerTest extends StudyCreateControllerMock {

    @Autowired MockMvc mvc;

    @Value("${custom.mapping.study.web_usr}")
    String mapping;

    @BeforeEach
    void setup() {
        getMemberIdMocking();
        createStudyMocking();
    }

    @Test
    @DisplayName("스터디 생성 api")
    void no1() throws Exception {
        String memberId = "1";
        StudyCreateReqDto reqDto = new StudyCreateReqDto();

        ResultActions result = postReq(mvc,
                mapping + "/v2/study", memberId, reqDto
        );

        result
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("studyId").value(1L))
                .andExpect(jsonPath("myStudyId").value(1L));
    }

    @Test
    @DisplayName("jwt 복호화 실패")
    void no2() throws Exception {
        String memberId = "0";
        StudyCreateReqDto reqDto = new StudyCreateReqDto();

        ResultActions result = postReq(mvc,
                mapping + "/v2/study", memberId, reqDto
        );

        result.andExpect(status().is4xxClientError());
    }
}