package com.baeker.study.testUtil.service.study;

import com.baeker.study.study.application.port.out.persistence.StudySnapshotRepositoryPort;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class SnapshotServiceMock {

    private StudySnapshotRepositoryPort repository =
            Mockito.mock(StudySnapshotRepositoryPort.class);

    public void snapshotSaveMocking() {
        when(repository.save(any()))
                .thenReturn(null);
    }

    public void snapshotDeleteMocking() {
        doNothing().when(repository).delete(any());
    }
}
