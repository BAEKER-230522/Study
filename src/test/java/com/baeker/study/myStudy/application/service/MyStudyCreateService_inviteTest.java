package com.baeker.study.myStudy.application.service;

import com.baeker.study.global.exception.InvalidDuplicateException;
import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.global.exception.OverLimitedException;
import com.baeker.study.myStudy.adapter.in.reqDto.InviteReqDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.testUtil.myStudy.MyStudyCreateMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeker.study.testUtil.CreateDomain.createStudy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("스터디 초대")
@ExtendWith(MockitoExtension.class)
class MyStudyCreateService_inviteTest extends MyStudyCreateMock {
    
    @InjectMocks
    private MyStudyCreateService createService;

    @BeforeEach
    void beforeEach() {
        baekjoonConnectCheckMocking();
        updateMyStudyMocking();
        getByMemberListMocking();
        repoSaveMocking();
    }

    @Test
    @DisplayName("초대 성공")
    void no1() {
        Long studyLeaderId = 1L;
        Study study = createStudy(studyLeaderId, 1L, "study", 10);

        Long invitee = 3L;

        Long myStudyId = inviteMember(studyLeaderId, invitee, study);
    }



    @Test
    @DisplayName("초대 대상이 백준 연동을 하지 않은 경우")
    void no2() {
        Long studyLeaderId = 1L;
        Study study = createStudy(studyLeaderId, 1L, "study", 10);

        Long invitee = 2L;

        assertThatThrownBy(() -> inviteMember(studyLeaderId, invitee, study))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("백준 연동이 안된 회원입니다.");
    }

    @Test
    @DisplayName("초대자가 리더가 아닌 경우")
    void no3() {
        Long studyLeaderId = 1L;
        Study study = createStudy(studyLeaderId, 1L, "study", 5);

        Long invitee = 3L;

        assertThatThrownBy(() -> inviteMember(studyLeaderId, invitee, study))
                .isInstanceOf(OverLimitedException.class)
                .hasMessageContaining("최대 인원에 도달한 스터디입니다.");
    }

    @Test
    @DisplayName("이미 가입 또는 대기중인 경우")
    void n4() {
        Long studyLeaderId = 1L;
        Study study = createStudy(studyLeaderId, 1L, "study", 10);

        Long invitee = 1L;

        assertThatThrownBy(() -> inviteMember(studyLeaderId, invitee, study))
                .isInstanceOf(InvalidDuplicateException.class)
                .hasMessageContaining("이미 가입 또는 가입 대기 중입니다.");
    }

    @Test
    @DisplayName("최대 인원에 도달한 경우")
    void no5() {
        Long studyLeaderId = 1L;
        Study study = createStudy(studyLeaderId, 1L, "study", 5);

        Long invitee = 3L;

        assertThatThrownBy(() -> inviteMember(studyLeaderId, invitee, study))
                .isInstanceOf(OverLimitedException.class)
                .hasMessageContaining("최대 인원에 도달한 스터디입니다.");

    }

    private Long inviteMember(Long studyLeaderId, Long invitee, Study study) {
        InviteReqDto dto = new InviteReqDto();
        dto.setInvitee(invitee);
        dto.setMsg("invite");
        return createService.invite(studyLeaderId, study, dto);
    }
}