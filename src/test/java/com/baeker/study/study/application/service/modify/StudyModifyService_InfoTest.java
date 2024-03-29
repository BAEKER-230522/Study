package com.baeker.study.study.application.service.modify;

import com.baeker.study.global.exception.service.NoPermissionException;
import com.baeker.study.global.dto.reqDto.StudyModifyReqDto;
import com.baeker.study.study.application.service.StudyModifyService;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.legacy.in.resDto.UpdateResDto;
import com.baeker.study.testUtil.service.study.StudyModifyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeker.study.testUtil.global.unit.CreateDomain.createStudy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("스터디 정보 수정")
@ExtendWith(MockitoExtension.class)
class StudyModifyService_InfoTest extends StudyModifyMock {

    @InjectMocks
    private StudyModifyService modifyService;

    @BeforeEach
    void setup() {
        repoSaveMocking();
    }

    @Test
    @DisplayName("수정 성공")
    void no1() {
        long memberId = 1L;
        Study study = createStudy(memberId, 1L, "study");

        UpdateResDto dto = modifyInfo(study, memberId);
    }

    @Test
    @DisplayName("수정 권한 없음")
    void no2() {
        long memberId = 1L;
        Study study = createStudy(memberId, 1L, "study");

        assertThatThrownBy(() -> modifyService.info(study, 2L, null))
                .isInstanceOf(NoPermissionException.class)
                .hasMessageContaining("권한이 없습니다.");
    }

    private UpdateResDto modifyInfo(Study study, Long memberId) {
        StudyModifyReqDto dto = new StudyModifyReqDto(1L, "update", "about", 5);
        return modifyService.info(study, memberId, dto);
    }
}