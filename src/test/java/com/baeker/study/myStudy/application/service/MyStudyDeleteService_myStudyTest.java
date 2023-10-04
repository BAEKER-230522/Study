package com.baeker.study.myStudy.application.service;

import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.testUtil.service.myStudy.MyStudyDeleteMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeker.study.myStudy.domain.entity.StudyStatus.*;
import static com.baeker.study.testUtil.global.CreateDomain.createMyStudy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("스터디 가입 거절과 탈퇴")
@ExtendWith(MockitoExtension.class)
class MyStudyDeleteService_myStudyTest extends MyStudyDeleteMock {

    @InjectMocks
    private  MyStudyDeleteService deleteService;

    @BeforeEach
    void beforeEach() {
        deleteMemberMocking();
        deleteMyStudyMocking();
    }

    @Test
    @DisplayName("스터디 가입 거절, 탈퇴 성공")
    void no1() {
        Long studyLeader = 10L;
        Long joinMember = 1L;
        Long inviteMember = 2L;
        Long studyMember = 3L;

        MyStudy join = createMyStudy(joinMember, "가입 요청", PENDING);
        MyStudy invitee = createMyStudy(inviteMember, "스터디 초대", INVITING);
        MyStudy member = createMyStudy(studyMember, null, MEMBER);

        deleteService.myStudy(studyLeader, join);
        deleteService.myStudy(inviteMember, invitee);
        deleteService.myStudy(studyMember, member);
    }

    @Test
    @DisplayName("당사자 이외의 회원이 삭제 요청한 경우")
    void no2() {
        MyStudy join = createMyStudy(1L, "", PENDING);

        assertThatThrownBy(() -> deleteService.myStudy(2L, join))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("권한이 없습니다.");
    }

    @Test
    @DisplayName("리더가 스터디를 탈퇴하는 경우")
    void no3() {
        Long leader = 10L;
        MyStudy myStudy = createMyStudy(leader, null, MEMBER);

        assertThatThrownBy(() -> deleteService.myStudy(leader, myStudy))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("스터디 장은 스터디를 탈퇴할 수 없습니다.");
    }

    @Test
    @DisplayName("탈퇴 회원 본인이 아닌 경우")
    void no4() {
        Long memberId = 1L;
        MyStudy myStudy = createMyStudy(memberId, null, MEMBER);

        assertThatThrownBy(() -> deleteService.myStudy(2L, myStudy))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("권한이 없습니다.");
    }
}