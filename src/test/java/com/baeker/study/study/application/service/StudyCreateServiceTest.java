package com.baeker.study.study.application.service;

import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.myStudy.application.port.in.MyStudyCreateUseCase;
import com.baeker.study.study.adapter.in.reqDto.StudyCreateReqDto;
import com.baeker.study.study.application.port.in.SnapshotUseCase;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.CreateResDto;
import com.baeker.study.testUtil.CreateStudy;
import com.baeker.study.testUtil.feign.MemberClientMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyCreateServiceTest extends MemberClientMock {

    @InjectMocks
    private StudyCreateService studyCreateService;
    @Mock
    private StudyRepositoryPort repository;
    @Mock
    private MyStudyCreateUseCase myStudyCreateUseCase;
    @Mock
    private SnapshotUseCase snapshotUseCase;

    @BeforeEach
    void beforeEach() {
        memberClientConnectCheckMocking();
    }

    @Test
    @DisplayName("study 생성")
    void no1() {
        Long memberId = 1L;
        Study study = createStudy(memberId, "스터디");
    }

    @Test
    @DisplayName("백준 연동이 안된경우")
    void no2() {
        Long memberId = 2L;
        assertThatThrownBy(() ->
                studyCreateService.study(memberId, null))
                .isInstanceOf(NoPermissionException.class);
    }

    private Study createStudy(Long memberId, String name) {
        when(repository.save(any()))
                .thenReturn(CreateStudy.createStudy(memberId, 1L, name));

        when(myStudyCreateUseCase.myStudy(eq(memberId), any()))
                .thenReturn(1L);

        doNothing().when(snapshotUseCase).createSnapshot(any(), any(), anyInt());

        StudyCreateReqDto reqDto = new StudyCreateReqDto(name, "about", 10);
        CreateResDto resDto = studyCreateService.study(memberId, reqDto);

        when(repository.findById(eq(resDto.getStudyId())))
                .thenReturn(Optional.ofNullable(
                        CreateStudy.createStudy(memberId, 1L, name)
                ));

        return repository.findById(resDto.getStudyId()).get();
    }
}