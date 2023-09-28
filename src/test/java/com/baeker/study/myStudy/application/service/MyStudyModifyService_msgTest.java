package com.baeker.study.myStudy.application.service;

import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.testUtil.CreateDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeker.study.myStudy.domain.entity.StudyStatus.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("스터디 가입 요청 메시지 수정")
@ExtendWith(MockitoExtension.class)
class MyStudyModifyService_msgTest {

    @InjectMocks
    private MyStudyModifyService modifyService;

    @Test
    @DisplayName("메시지 수정 성공")
    void no1() {
        Long memberId = 1L;
        MyStudy myStudy = CreateDomain.createMyStudy(memberId, "join", PENDING);

        modifyService.msg(memberId, myStudy, "hello");
    }

    @Test
    @DisplayName("이미 정회원인 경우")
    void no2() {
        Long memberId = 1L;
        MyStudy myStudy = CreateDomain.createMyStudy(memberId, "", MEMBER);

        assertThatThrownBy(() -> modifyService.msg(memberId, myStudy, "hi"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 승인된 스터디 맴버입니다.");
    }

    @Test
    @DisplayName("가입 요청한 회원이 아닌 경우")
    void no3() {
        Long memberId = 1L;
        MyStudy myStudy = CreateDomain.createMyStudy(memberId, "", PENDING);

        assertThatThrownBy(() -> modifyService.msg(2L, myStudy, "hi"))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("수정 권한이 없습니다.");
    }

    @Test
    @DisplayName("초대한 회원이 아닌 경우")
    void no4() {
        Long invitee = 1L;
        MyStudy myStudy = CreateDomain.createMyStudy(invitee, "", INVITING);

        assertThatThrownBy(() -> modifyService.msg(invitee, myStudy, "hi"))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("수정 권한이 없습니다.");
    }
}