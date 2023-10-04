package com.baeker.study.study.adapter.in.web;

import com.baeker.study.study.adapter.in.reqDto.StudyCreateReqDto;
import com.baeker.study.testUtil.adapter.web.StudyCreateControllerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static com.baeker.study.testUtil.global.JsonMapper.toJson;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("스터디 생성 api 테스트")
@SpringJUnitConfig
@WebMvcTest(StudyCreateController.class)
class StudyCreateControllerTest extends StudyCreateControllerMock {

    @Autowired
    MockMvc mvc;

    @Value("${custom.mapping.study.web}")
    String mapping;

    @BeforeEach
    void setup() {
        getMemberIdMocking();
        createStudyMocking();
    }

    @Test
    @DisplayName("스터디 생성 api")
    void no1() throws Exception {
        String name = "study";
        String about = "hi";
        Integer capacity = 10;
        String reqDto = createReqDto(name, about, capacity);

        mvc.perform(post(mapping + "/v2/study")
                .contentType(APPLICATION_JSON)
                .header("Authorization", "1")
                .content(reqDto)

        ).andExpect(
                status().is2xxSuccessful()
        ).andExpect(
                jsonPath("studyId").value(1L)
        ).andExpect(
                jsonPath("myStudyId").value(1L)
        );
    }

    @Test
    @DisplayName("jwt 복호화 실패")
    void no2() throws Exception {
        String name = "study";
        String about = "hi";
        Integer capacity = 10;
        String reqDto = createReqDto(name, about, capacity);

        mvc.perform(post(mapping + "/v2/study")
                .contentType(APPLICATION_JSON)
                .header("Authorization", "0")
                .content(reqDto)
        ).andExpect(
                status().is4xxClientError()
        );
    }

    private String createReqDto(String name, String about, Integer capacity) {
        StudyCreateReqDto reqDto = new StudyCreateReqDto(name, about, capacity);
        return toJson(reqDto);
    }
}