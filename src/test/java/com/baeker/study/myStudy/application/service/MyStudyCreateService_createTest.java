package com.baeker.study.myStudy.application.service;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.testUtil.myStudy.MyStudyCreateMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeker.study.testUtil.CreateStudy.createStudy;

@DisplayName("My Study 생성")
@ExtendWith(MockitoExtension.class)
class MyStudyCreateService_createTest extends MyStudyCreateMock {

    @InjectMocks
    private MyStudyCreateService myStudyCreateService;

    @BeforeEach
    void beforeEach() {
        updateMyStudyMocking();
        repoSaveMocking();
    }

    @Test
    @DisplayName("생성")
    void no1() {
        Long memberId = 1L;
        Study study = createStudy(memberId, 1L, "study");

        Long myStudyId = myStudyCreateService.myStudy(memberId, study);
    }
}