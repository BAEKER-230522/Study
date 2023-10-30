package com.baeker.study.myStudy.application.service;

import com.baeker.study.global.exception.service.NoPermissionException;
import com.baeker.study.global.exception.service.NotFoundException;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.testUtil.service.myStudy.MyStudyDeleteMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeker.study.myStudy.domain.entity.StudyStatus.MEMBER;
import static com.baeker.study.myStudy.domain.entity.StudyStatus.PENDING;
import static com.baeker.study.testUtil.global.unit.CreateDomain.createMyStudy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("단위 - 스터디원 강퇴")
@ExtendWith(MockitoExtension.class)
class MyStudyDeleteService_dropOutTest extends MyStudyDeleteMock {

    @InjectMocks
    private  MyStudyDeleteService deleteService;

    @BeforeEach
    void setup() {
        deleteMemberMocking();
        deleteMyStudyMocking();
    }

    @Test
    @DisplayName("강퇴 성공")
    void no1() {
        Long leaderId = 10L;
        MyStudy myStudy = createMyStudy(1L, null, MEMBER);

        deleteService.dropOut(leaderId, myStudy);
    }

    @Test
    @DisplayName("대상이 정회원이 아닌경우")
    void no2() {
        Long leaderId = 10L;
        MyStudy myStudy = createMyStudy(1L, "", PENDING);

        assertThatThrownBy(() -> deleteService.dropOut(leaderId, myStudy))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("스터디의 정회원이 아닙니다.");
    }

    @Test
    @DisplayName("스터디장이 아닌경우")
    void no3() {
        Long memberId = 1L;
        MyStudy myStudy = createMyStudy(memberId, null, MEMBER);

        assertThatThrownBy(() -> deleteService.dropOut(memberId, myStudy))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("권한이 없습니다.");
    }
}