package com.baeker.study.myStudy.application.service;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.myStudy.application.port.out.persistence.MyStudyRepositoryPort;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.testUtil.MemberClientMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.baeker.study.testUtil.CreateStudy.CreateStudy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyStudyCreateServiceTest extends MemberClientMock {

    @InjectMocks
    private MyStudyCreateService myStudyCreateService;
    @Mock
    private MyStudyRepositoryPort repository;

    @Test
    @DisplayName("my study 생성")
    void no1() {
        Long memberId = 1L;
        Study study = CreateStudy("study", memberId);

        MyStudy myStudy = createMyStudy(memberId, study);

        assertThat(myStudy.getStudy().getName()).isEqualTo("study");
    }

    private MyStudy createMyStudy(Long memberId, Study study) {
        MyStudy saveMyStudy = MyStudy.createNewStudy(memberId, study);
        when(repository.save(any()))
                .thenReturn(saveMyStudy);

        Long myStudyId = myStudyCreateService.myStudy(memberId, study);

        when(repository.findById(myStudyId))
                .thenReturn(Optional.of(saveMyStudy));
        return repository.findById(myStudyId).get();
    }

}