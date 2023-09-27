package com.baeker.study.study.application.service.modify;

import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.study.adapter.in.reqDto.StudyModifyReqDto;
import com.baeker.study.study.application.service.StudyModifyService;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.UpdateResDto;
import com.baeker.study.testUtil.StudyModifyRepositoryMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeker.study.testUtil.CreateStudy.CreateStudy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class StudyModifyService_InfoTest extends StudyModifyRepositoryMock {

    @InjectMocks
    private StudyModifyService studyModifyService;

    @Test
    @DisplayName("스터디 정보 수정")
    void no1() {
        long memberId = 1L;
        Study study = CreateStudy("study", memberId);

        UpdateResDto dto = modifyInfo(study, memberId);
    }

    @Test
    @DisplayName("수정 권한 없음")
    void no2() {
        long memberId = 1L;
        Study study = CreateStudy("study", memberId);

        assertThatThrownBy(() -> studyModifyService.info(study, 2L, null))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("권한이 없습니다.");
    }

    private UpdateResDto modifyInfo(Study study, Long memberId) {
        StudyModifyReqDto dto = new StudyModifyReqDto(1L, "update", "about", 5);
        return studyModifyService.info(study, memberId, dto);
    }
}