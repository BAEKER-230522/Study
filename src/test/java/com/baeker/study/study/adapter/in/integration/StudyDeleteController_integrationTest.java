package com.baeker.study.study.adapter.in.integration;


import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.baeker.study.testUtil.global.integration.CreateRow;
import com.baeker.study.testUtil.global.integration.MemberClientIntegrationMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.baeker.study.testUtil.global.integration.CreateRow.createStudy;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("스터디 삭제 통합 테스트")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StudyDeleteController_integrationTest extends MemberClientIntegrationMock {

    @Autowired MockMvc mvc;
    @Autowired StudyQueryUseCase studyQueryUseCase;

    @BeforeEach
    void setup() {
        baekjoonConnectCheckMocking();
        updateMyStudyMocking();
        deleteMemberMocking();
    }

    @Test
    @DisplayName("스터디 삭제 api")
    void no1() throws Exception {
        createStudy(mvc, 1, 3, jwt1);
        createStudy(mvc, 2, 3, jwt2);
        createStudy(mvc, 3, 3, jwt3);

        List<StudyResDto> all = studyQueryUseCase.all();

        assertThat(all.size()).isEqualTo(3);
    }
}