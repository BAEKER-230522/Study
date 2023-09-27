package com.baeker.study.testUtil;

import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StudyModifyRepositoryMock {

    private StudyRepositoryPort repository =
            Mockito.mock(StudyRepositoryPort.class);

    @BeforeEach
    void beforeEach() {
        when(repository.save(any())).thenAnswer(invocation -> {
            Study study = (Study) invocation.getArgument(0);

            return study;
        });
    }
}
