package com.baeker.study.myStudy.application.service;

import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.testUtil.global.CreateDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeker.study.myStudy.domain.entity.StudyStatus.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("스터디 가입 승락")
@ExtendWith(MockitoExtension.class)
class MyStudyModifyService_acceptTest {

    @InjectMocks
    private MyStudyModifyService modifyService;


    @Test
    @DisplayName("승락 성공")
    void no1() {
        Long memberId = 1L;
        MyStudy myStudy = CreateDomain.createMyStudy(memberId, "join", PENDING);

        modifyService.accept(10L, myStudy);
    }

    @Test
    @DisplayName("이미 정회원인 경우")
    void no2() {
        Long memberId = 1L;
        MyStudy myStudy = CreateDomain.createMyStudy(memberId, "", MEMBER);

        assertThatThrownBy(() -> modifyService.accept(10L, myStudy))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 승인된 스터디 맴버입니다.");
    }

    @Test
    @DisplayName("가입 요청시 리더가 아닌 경우")
    void no3() {
        Long memberId = 1L;
        MyStudy myStudy = CreateDomain.createMyStudy(memberId, "", PENDING);

        assertThatThrownBy(() -> modifyService.accept(memberId, myStudy))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("수정 권한이 없습니다.");
    }

    @Test
    @DisplayName("초대시 당사자가 아닌 경우")
    void no4() {
        Long memberId = 1L;
        MyStudy myStudy = CreateDomain.createMyStudy(memberId, "", INVITING);

        assertThatThrownBy(() -> modifyService.accept(10L, myStudy))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("수정 권한이 없습니다.");
    }
}