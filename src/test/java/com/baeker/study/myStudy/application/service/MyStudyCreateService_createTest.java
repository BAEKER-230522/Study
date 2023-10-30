package com.baeker.study.myStudy.application.service;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.testUtil.service.myStudy.MyStudyCreateMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeker.study.testUtil.global.unit.CreateDomain.createStudy;

@DisplayName("단위 - My Study 생성")
@ExtendWith(MockitoExtension.class)
class MyStudyCreateService_createTest extends MyStudyCreateMock {

    @InjectMocks
    private MyStudyCreateService createService;

    @BeforeEach
    void setup() {
        updateMyStudyMocking();
        repoSaveMocking();
    }

    @Test
    @DisplayName("생성")
    void no1() {
        Long memberId = 1L;
        Study study = createStudy(memberId, 1L, "study");

        Long myStudyId = createService.myStudy(memberId, study);
    }
}