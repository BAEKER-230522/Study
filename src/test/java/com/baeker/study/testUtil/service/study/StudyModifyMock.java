package com.baeker.study.testUtil.service.study;

import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.baeker.study.testUtil.global.unit.MemberClientUnitMock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class StudyModifyMock extends MemberClientUnitMock {

    private StudyRepositoryPort repository =
            Mockito.mock(StudyRepositoryPort.class);

    private StudyQueryUseCase studyQueryUseCase =
            Mockito.mock(StudyQueryUseCase.class);

    public void repoSaveMocking() {
        when(repository.save(any())).thenAnswer(invocation -> {
            Study study = (Study) invocation.getArgument(0);

            return study;
        });
    }

    public void getByMemberIdMocking() {
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
