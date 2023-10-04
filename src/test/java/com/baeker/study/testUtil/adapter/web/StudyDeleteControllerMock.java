package com.baeker.study.testUtil.adapter.web;

import com.baeker.study.study.application.port.in.StudyDeleteUseCase;
import com.baeker.study.testUtil.global.StudyQueryUseCaseMock;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

public class StudyDeleteControllerMock extends StudyQueryUseCaseMock {

    @MockBean
    private StudyDeleteUseCase deleteUseCase;

    public void deleteStudyMocking() {
        doNothing().when(deleteUseCase).study(any(), anyLong());
    }
}