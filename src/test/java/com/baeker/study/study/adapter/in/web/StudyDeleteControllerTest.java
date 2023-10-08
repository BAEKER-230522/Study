package com.baeker.study.study.adapter.in.web;

import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.testUtil.adapter.web.StudyDeleteControllerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.baeker.study.testUtil.global.integration.MockMvcRequest.deleteReq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("study 삭제 api")
@SpringJUnitConfig
@WebMvcTest(StudyDeleteController.class)
class StudyDeleteControllerTest extends StudyDeleteControllerMock {

    @Autowired MockMvc mvc;
    @Autowired StudyQueryUseCase studyQueryUseCase;

    @Value("${custom.mapping.study.web}")
    String mapping;

    @BeforeEach
    void setup() {
        getMemberIdMocking();
        findStudyByIdMocking();
        deleteStudyMocking();
    }

    @Test
    @DisplayName("study 삭제")
    void no1() throws Exception {
        String memberId = "1";
        Long studyId = 1L;

        ResultActions result = deleteReq(mvc,
                mapping + "/v2/{studyId}",
                memberId,
                studyId
        );

        result.andExpect(status().is2xxSuccessful());
    }
}