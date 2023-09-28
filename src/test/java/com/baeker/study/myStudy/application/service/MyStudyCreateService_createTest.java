package com.baeker.study.myStudy.application.service;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.testUtil.MyStudyCreateMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeker.study.myStudy.domain.entity.MyStudy.createNewStudy;
import static com.baeker.study.testUtil.CreateStudy.createStudy;

@ExtendWith(MockitoExtension.class)
class MyStudyCreateService_createTest extends MyStudyCreateMock {

    @InjectMocks
    private MyStudyCreateService myStudyCreateService;

    @BeforeEach
    void beforeEach() {
        memberClientMocking();
        memberClientUpdateMyStudyMocking();
        repoSaveMocking();
    }

    @Test
    @DisplayName("my study 생성")
    void no1() {
        Long memberId = 1L;
        Study study = createStudy(memberId, 1L, "study");

        Long myStudyId = createMyStudy(memberId, study);
    }

    private Long createMyStudy(Long memberId, Study study) {
        MyStudy saveMyStudy = createNewStudy(memberId, study);
        return myStudyCreateService.myStudy(memberId, study);
    }
}