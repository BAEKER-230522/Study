package com.baeker.study.study.application.service;

import com.baeker.study.global.exception.service.NoPermissionException;
import com.baeker.study.global.exception.service.NotFoundException;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.legacy.in.reqDto.UpdateLeaderReqDto;
import com.baeker.study.study.legacy.in.resDto.StudyResDto;
import com.baeker.study.testUtil.service.study.StudyModifyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeker.study.testUtil.global.unit.CreateDomain.createStudy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("단위 - 스터디 리더 위임")
@ExtendWith(MockitoExtension.class)
class StudyModifyService_leaderTest extends StudyModifyMock {

    @InjectMocks
    private StudyModifyService modifyService;

    @BeforeEach
    void setup() {
        repoSaveMocking();
        getByMemberIdMocking();
    }

    @Test
    @DisplayName("리더 위임 성공")
    void no1() {
        Long memberId = 1L;
        Long newLeaderId = 2L;
        Study study = createStudy(memberId, 1L, "study1");

        StudyResDto dto = modifyLeader(study, memberId, newLeaderId);
    }

    @Test
    @DisplayName("study 리더가 아닌 경우")
    void no2() {
        Long memberId = 1L;
        Long newLeaderId = 2L;
        Study study = createStudy(memberId, 1L, "study1");

        assertThatThrownBy(() -> modifyLeader(study, newLeaderId, newLeaderId))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("권한이 없습니다.");
    }

    @Test
    @DisplayName("study 에 가입하지 않은 회원에게 리더를 위임하는 경우")
    void no3() {
        Long memberId = 1L;
        Long newLeaderId = 3L;
        Study study = createStudy(memberId, 1L, "study1");

        assertThatThrownBy(() -> modifyLeader(study, memberId, newLeaderId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("가입된 회원이 아닙니다.");
    }

    private StudyResDto modifyLeader(Study study, Long memberId, Long newLeaderId) {
        UpdateLeaderReqDto dto = new UpdateLeaderReqDto();
        dto.setStudyId(study.getId());
        dto.setNewLeader(newLeaderId);
        return modifyService.leader(study, memberId, dto);
    }
}