package com.baeker.study.testUtil.adapter.web;

import com.baeker.study.study.application.port.in.StudyModifyUseCase;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.UpdateLeaderReqDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.baeker.study.study.in.resDto.UpdateResDto;
import com.baeker.study.testUtil.global.StudyQueryUseCaseMock;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class StudyModifyControllerMock extends StudyQueryUseCaseMock {

    @MockBean
    private StudyModifyUseCase modifyUseCase;

    public void modifyInfoMocking() {
        when(modifyUseCase.info(any(), anyLong(), any()))
                .thenAnswer(invocation -> {
                    Study study = (Study) invocation.getArgument(0);
                    return new UpdateResDto(study.getId());
                });
    }

    public void modifyLeaderMocking() {
        StudyResDto dto = new StudyResDto();

        when(modifyUseCase.leader(any(), anyLong(), any()))
                .thenAnswer(invocation -> {
                    UpdateLeaderReqDto reqDto = (UpdateLeaderReqDto) invocation.getArgument(2);
                    dto.setLeader(reqDto.getNewLeader());
                    return dto;
                });
    }
}
