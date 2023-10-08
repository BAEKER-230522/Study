package com.baeker.study.study.adapter.in.integration;

import com.baeker.study.study.adapter.in.reqDto.StudyCreateReqDto;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.CreateResDto;
import com.baeker.study.testUtil.global.integration.MemberClientIntegrationMock;
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

import static com.baeker.study.testUtil.global.integration.MockMvcRequest.postReq;
import static com.baeker.study.testUtil.global.integration.MockMvcRequest.toResDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("스터디 생성 통합 테스트")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StudyCreateController_integrationTest extends MemberClientIntegrationMock {

    @Autowired MockMvc mvc;
    @Autowired StudyQueryUseCase studyQueryUseCase;

    @Value("${custom.mapping.study.web}")
    String mapping;

    @Value("${custom.jwt.test1}")
    String jwt;

    @BeforeEach
    void setup() {
        baekjoonConnectCheckMocking();
        updateMyStudyMocking();
    }

    @Test
    @DisplayName("스터디 생성 api")
    void no1() throws Exception {
        StudyCreateReqDto reqDto = createReqDto("스터디", "하이", 10);

        ResultActions result = postReq(mvc, mapping + "/v2/study", jwt, reqDto);
        CreateResDto resDto = toResDto(result, CreateResDto.class);


        result.andExpect(status().is2xxSuccessful());

        Study study = studyQueryUseCase.byId(resDto.getStudyId());
        assertThat(study.getName()).isEqualTo("스터디");
        assertThat(study.getAbout()).isEqualTo("하이");
        assertThat(study.getCapacity()).isEqualTo(10);
        assertThat(study.getLeader()).isEqualTo(1L);
        assertThat(study.getSnapshots().size()).isEqualTo(7);
    }

    private StudyCreateReqDto createReqDto(String name, String about, Integer capacity) {
       return new StudyCreateReqDto(name, about, capacity);
    }
}