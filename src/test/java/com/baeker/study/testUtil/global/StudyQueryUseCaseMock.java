package com.baeker.study.testUtil.global;

import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.baeker.study.testUtil.global.CreateDomain.createStudy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StudyQueryUseCaseMock extends JwtDecryptMock {

    @MockBean
    StudyQueryUseCase queryUseCase;

    public void findStudyByIdMocking() {
        when(queryUseCase.byId(any()))
                .thenAnswer(invocation -> {
                    Long studyId = (Long) invocation.getArgument(0);

                    return createStudy(1L, studyId, "findStudy");
                });
    }
}
