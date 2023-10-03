package com.baeker.study.testUtil.myStudy;

import com.baeker.study.myStudy.application.port.out.persistence.MyStudyRepositoryPort;
import com.baeker.study.testUtil.feign.MemberClientMock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

public class MyStudyDeleteMock extends MemberClientMock {

    private MyStudyRepositoryPort repository =
            Mockito.mock(MyStudyRepositoryPort.class);

    public void deleteMyStudyMocking() {
        doNothing().when(repository).delete(any());
    }
}
