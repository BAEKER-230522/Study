package com.baeker.study.study.application.service;

import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.global.exception.NotFoundException;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.UpdateLeaderReqDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.baeker.study.testUtil.StudyModifyRepositoryMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.baeker.study.testUtil.CreateStudy.createStudy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyModifyService_leaderTest extends StudyModifyRepositoryMock {

    @InjectMocks
    private StudyModifyService studyModifyService;
    @Mock
    private StudyQueryUseCase studyQueryUseCase;

    @Test
    @DisplayName("study 리더 위임")
    void no1() {
        mocking();
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
                .hasMessageContaining("스터디 리더만 위임이 가능합니다.");
    }

    @Test
    @DisplayName("study 에 가입하지 않은 회원에게 리더를 위임하는 경우")
    void no3() {
        mocking();
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
        return studyModifyService.leader(study, memberId, dto);
    }

    void mocking() {
        ArrayList<StudyResDto> dtos = new ArrayList<>();
        when(studyQueryUseCase.byMemberId(anyLong(), anyInt()))
                .thenAnswer(invocation -> {
                    Long newMemberId = (Long) invocation.getArgument(0);

                    if (newMemberId == 3L) return dtos;

                    StudyResDto dto = new StudyResDto();
                    dto.setId(1L);
                    dtos.add(dto);
                    return dtos;
                });
    }
}