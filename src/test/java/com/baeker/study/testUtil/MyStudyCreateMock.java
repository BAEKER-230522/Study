package com.baeker.study.testUtil;

import com.baeker.study.myStudy.application.port.out.persistence.MyStudyRepositoryPort;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.in.resDto.MemberResDto;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MyStudyCreateMock extends MemberClientMock {

    private MyStudyRepositoryPort repository =
            Mockito.mock(MyStudyRepositoryPort.class);
    private StudyQueryUseCase studyQueryUseCase =
            Mockito.mock(StudyQueryUseCase.class);


    public void studyQueryUesCaseMocking() {
        ArrayList<MemberResDto> dtos = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            dtos.add(new MemberResDto());

        when(studyQueryUseCase.byMemberList(any()))
                .thenReturn(dtos);
    }

    public void repoSaveMocking() {
        when(repository.save(any()))
                .thenAnswer(invocation -> {
                    MyStudy myStudy = (MyStudy) invocation.getArgument(0);

                    return myStudy;
                });
    }
}
