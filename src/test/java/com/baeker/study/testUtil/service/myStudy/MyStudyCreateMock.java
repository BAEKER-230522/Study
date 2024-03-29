package com.baeker.study.testUtil.service.myStudy;

import com.baeker.study.myStudy.application.port.out.persistence.MyStudyRepositoryPort;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.legacy.in.resDto.MemberResDto;
import com.baeker.study.testUtil.global.unit.MemberClientUnitMock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MyStudyCreateMock extends MemberClientUnitMock {

    private MyStudyRepositoryPort repository =
            Mockito.mock(MyStudyRepositoryPort.class);
    private StudyQueryUseCase studyQueryUseCase =
            Mockito.mock(StudyQueryUseCase.class);


    public void getByMemberListMocking() {
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
