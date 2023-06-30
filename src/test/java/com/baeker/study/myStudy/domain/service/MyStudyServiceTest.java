package com.baeker.study.myStudy.domain.service;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.in.reqDto.JoinMyStudyReqDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.out.SnapshotRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class MyStudyServiceTest {

    @Autowired
    private StudyService studyService;
    @Autowired
    private MyStudyService myStudyService;
    @MockBean
    private MemberClient memberClient;

    @BeforeEach
    public void beforeEach() {
        when(memberClient.updateMyStudy(any()))
                .thenReturn(new RsData<>("S-1", "성공", null));
    }

    @Test
    @DisplayName("join")
    public void no1() {
        Study study = study(1L, "study1", "about", "member1");

        MyStudy myStudy = myStudyService.join(
                new JoinMyStudyReqDto(study.getId(), 2L, "가입 요청"), study
        );

        List<MyStudy> all = myStudyService.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

    private Study study(Long member, String name, String about, String leader) {
        MyStudy myStudy = studyService.create(CreateReqDto.createStudy(member, name, about, leader, 10));
        Study study = myStudy.getStudy();
        return study;
    }
}