package com.baeker.study.study.application.service;

import com.baeker.study.global.exception.service.NoPermissionException;
import com.baeker.study.study.adapter.in.reqDto.StudyCreateReqDto;
import com.baeker.study.study.in.resDto.CreateResDto;
import com.baeker.study.testUtil.service.study.StudyCreateMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("스터디 생성")
@ExtendWith(MockitoExtension.class)
class StudyCreateServiceTest extends StudyCreateMock {

    @InjectMocks
    private StudyCreateService createService;

    @BeforeEach
    void setup() {
        baekjoonConnectCheckMocking();
        repoSaveMocking();
        createMyStudyMocking();
        createSnapshotMocking();
    }

    @Test
    @DisplayName("생성 성공")
    void no1() {
        Long memberId = 1L;

        CreateResDto resDto = createStudy(memberId, "스터디");
    }

    private CreateResDto createStudy(Long memberId, String name) {
        StudyCreateReqDto reqDto = new StudyCreateReqDto(name, "about", 10);
        return createService.study(memberId, reqDto);
    }

    @Test
    @DisplayName("백준 연동이 안된경우")
    void no2() {
        Long memberId = 2L;

        assertThatThrownBy(() ->
                createService.study(memberId, null))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("백준 연동이 안된 user");
    }
}