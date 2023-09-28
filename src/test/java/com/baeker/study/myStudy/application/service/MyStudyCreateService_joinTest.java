package com.baeker.study.myStudy.application.service;

import com.baeker.study.global.exception.InvalidDuplicateException;
import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.global.exception.OverLimitedException;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.testUtil.myStudy.MyStudyCreateMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeker.study.testUtil.CreateStudy.createStudy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("스터디 가입 신청")
@ExtendWith(MockitoExtension.class)
class MyStudyCreateService_joinTest extends MyStudyCreateMock {

    @InjectMocks
    private MyStudyCreateService myStudyCreateService;

    @BeforeEach
    void beforeEach() {
        memberClientConnectCheckMocking();
        memberClientUpdateMyStudyMocking();
        studyQueryUesCaseMocking();
        repoSaveMocking();
    }

    @Test
    @DisplayName("가입 신청")
    void no1() {
        Long studyLeaderId = 1L;
        Study study = createStudy(studyLeaderId, 1L, "study", 10);
        Long memberId = 3L;

        Long myStudyId = myStudyCreateService.join(memberId, study, "msg");
    }

    @Test
    @DisplayName("백준 연동 안하고 가입 신청 할 경우")
    void no2() {
        Long studyLeaderId = 1L;
        Study study = createStudy(studyLeaderId, 1L, "study", 10);
        Long memberId = 2L;

        assertThatThrownBy(() -> myStudyCreateService.join(memberId, study, "msg"))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("백준 연동이 안된 회원입니다.");
    }

    @Test
    @DisplayName("이미 가입한 상태로 한번더 가입 신청할 경우")
    void no3() {
        Long studyLeaderId = 1L;
        Study study = createStudy(studyLeaderId, 1L, "study", 10);
        Long memberId = 1L;

        assertThatThrownBy(() -> myStudyCreateService.join(memberId, study, "msg"))
                .isInstanceOf(InvalidDuplicateException.class)
                .hasMessageContaining("이미 가입 또는 가입 대기 중입니다.");
    }

    @Test
    @DisplayName("최대 인원에 도달한 스터디에 가입 신청하는 경우")
    void no4() {
        Long studyLeaderId = 1L;
        Study study = createStudy(studyLeaderId, 1L, "study", 5);
        Long memberId = 3L;

        assertThatThrownBy(() -> myStudyCreateService.join(memberId, study, "msg"))
                .isInstanceOf(OverLimitedException.class)
                .hasMessageContaining("최대 인원에 도달한 스터디입니다.");
    }
}