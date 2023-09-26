package com.baeker.study.study.application.service;

import com.baeker.study.myStudy.application.port.in.MyStudyCreateUseCase;
import com.baeker.study.study.adapter.in.reqDto.StudyCreateReqDto;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.CreateResDto;
import com.baeker.study.testUtil.MemberClientMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.baeker.study.testUtil.CreateStudy.CreateStudy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyCreateServiceTest extends MemberClientMock {

    @InjectMocks
    private StudyCreateService studyCreateService;
    @Mock
    private StudyRepositoryPort repository;
    @Mock
    private MyStudyCreateUseCase myStudyCreateUseCase;

    @Test
    @DisplayName("study 생성")
    void no1() {
        Long memberId = 1L;

        Study study = createStudy(memberId, "스터디");

        assertThat(study.getName()).isEqualTo("스터디");
    }

    private Study createStudy(Long memberId, String name) {
        when(repository.save(any()))
                .thenReturn(CreateStudy(name, memberId));

        when(myStudyCreateUseCase.myStudy(eq(memberId), any()))
                .thenReturn(1L);

        StudyCreateReqDto reqDto = new StudyCreateReqDto(name, "about", 10);
        CreateResDto resDto = studyCreateService.study(memberId, reqDto);

        when(repository.findById(eq(resDto.getStudyId())))
                .thenReturn(Optional.ofNullable(
                        CreateStudy(name, memberId)
                ));

        return repository.findById(resDto.getStudyId()).get();
    }
}