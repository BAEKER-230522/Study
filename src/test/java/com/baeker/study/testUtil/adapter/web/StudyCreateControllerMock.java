package com.baeker.study.testUtil.adapter.web;

import com.baeker.study.study.application.port.in.StudyCreateUseCase;
import com.baeker.study.study.in.resDto.CreateResDto;
import com.baeker.study.testUtil.global.unit.JwtDecryptMock;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class StudyCreateControllerMock extends JwtDecryptMock {

    @MockBean
    private StudyCreateUseCase createUseCase;

    public void createStudyMocking() {
        when(createUseCase.study(anyLong(), any()))
                .thenReturn(new CreateResDto(1L, 1L));
    }
}