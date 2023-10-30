package com.baeker.study.testUtil.service.study;

import com.baeker.study.myStudy.application.port.in.MyStudyCreateUseCase;
import com.baeker.study.study.application.port.in.SnapshotUseCase;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.testUtil.global.unit.MemberClientUnitMock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class StudyCreateMock extends MemberClientUnitMock {

    private StudyRepositoryPort repository =
            Mockito.mock(StudyRepositoryPort.class);
    private MyStudyCreateUseCase myStudyCreateUseCase =
            Mockito.mock(MyStudyCreateUseCase.class);
    private SnapshotUseCase snapshotUseCase =
            Mockito.mock(SnapshotUseCase.class);

    public void repoSaveMocking() {
        when(repository.save(any()))
                .thenAnswer(invocation -> {
                    Study study = (Study) invocation.getArgument(0);
                    return study;
                });
    }

    public void createMyStudyMocking() {
        when(myStudyCreateUseCase.myStudy(anyLong(), any()))
                .thenReturn(1L);
    }

    public void createSnapshotMocking() {
        doNothing().when(snapshotUseCase).createSnapshot(any(), any(), anyInt());
    }
}
