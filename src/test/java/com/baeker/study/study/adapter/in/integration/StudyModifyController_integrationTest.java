package com.baeker.study.study.adapter.in.integration;

import com.baeker.study.study.adapter.in.reqDto.StudyModifyReqDto;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.UpdateLeaderReqDto;
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

import static com.baeker.study.testUtil.global.integration.CreateRow.addMemberToStudy;
import static com.baeker.study.testUtil.global.integration.CreateRow.createStudy;
import static com.baeker.study.testUtil.global.integration.MockMvcRequest.patchReq;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("스터디 수정 통합 테스트")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StudyModifyController_integrationTest extends MemberClientIntegrationMock {

    @Autowired
    MockMvc mvc;
    @Autowired
    StudyQueryUseCase studyQueryUseCase;

    @Value("${custom.mapping.study.web}")
    String mapping;

    @Value("${custom.jwt.test1}")
    String jwt1;

    @Value("${custom.jwt.test3}")
    String jwt3;

    @BeforeEach
    void setup() throws Exception {
        baekjoonConnectCheckMocking();
        createStudy(mvc, 1, 10, jwt1);
        addMemberToStudy(mvc, 1L, jwt3);
    }

    @Test
    @DisplayName("스터디 기본 정보 수정")
    void no1() throws Exception {
        Study study = studyQueryUseCase.byId(1L);
        StudyModifyReqDto reqDto = createModifyReqDto(study.getId(), "이름 수정", "소개 수정", 3);

        ResultActions result = patchReq(mvc,
                mapping + "/v2/info", jwt1, reqDto
        );

        result.andExpect(status().is2xxSuccessful());

        study = studyQueryUseCase.byId(1L);
        assertThat(study.getName()).isEqualTo("이름 수정");
        assertThat(study.getAbout()).isEqualTo("소개 수정");
        assertThat(study.getCapacity()).isEqualTo(3);
    }

    @Test
    @DisplayName("스터디장 위임 api ")
    void no2() throws Exception {
        Study study = studyQueryUseCase.byId(1L);
        UpdateLeaderReqDto reqDto = createLeaderReqDto(study.getId(), 3L);

        ResultActions result = patchReq(mvc,
                mapping + "/v2/leader", jwt1, reqDto
        );

        result.andExpect(status().is2xxSuccessful());
    }

    private StudyModifyReqDto createModifyReqDto(Long studyId, String name, String about, int capacity) {
        return new StudyModifyReqDto(studyId, name, about, capacity);
    }

    private UpdateLeaderReqDto createLeaderReqDto(Long studyId, Long newLeader) {
        UpdateLeaderReqDto dto = new UpdateLeaderReqDto();
        dto.setStudyId(studyId);
        dto.setNewLeader(newLeader);
        return dto;
    }
}