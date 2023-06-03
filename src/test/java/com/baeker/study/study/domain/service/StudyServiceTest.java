package com.baeker.study.study.domain.service;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.event.AddSolvedCountEvent;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StudyServiceTest {

    @Autowired private StudyService studyService;
    @Autowired private MyStudyService myStudyService;
    @Autowired private ApplicationEventPublisher publisher;


    @Test
    void 이벤트로_해결한_문제_업데이트() {
        Study study1 = createStudy(1L, "study1", "about", "member1");
        Study study2 = createStudy(1L, "study2", "about", "member1");
        Study study3 = createStudy(1L, "study3", "about", "member1");
        Study study4 = createStudy(2L, "study4", "about", "member2");
        Study study5 = createStudy(2L, "study5", "about", "member2");
        Study study6 = createStudy(2L, "study6", "about", "member2");

        List<Study> all = studyService.findAll();
        assertThat(all.size()).isEqualTo(6);
        assertThat(study1.solvedCount()).isEqualTo(0);

        publisher.publishEvent(new AddSolvedCountEvent(this, 1L, 1, 1, 1, 1, 1, 1));

        assertThat(study1.solvedCount()).isEqualTo(6);
        assertThat(study2.solvedCount()).isEqualTo(6);
        assertThat(study3.solvedCount()).isEqualTo(6);
        assertThat(study4.solvedCount()).isEqualTo(0);
        assertThat(study5.solvedCount()).isEqualTo(0);
        assertThat(study6.solvedCount()).isEqualTo(0);

        publisher.publishEvent(new AddSolvedCountEvent(this, 2L, 3, 3, 3, 3, 3, 3));

        assertThat(study1.solvedCount()).isEqualTo(6);
        assertThat(study4.solvedCount()).isEqualTo(18);
        assertThat(study5.solvedCount()).isEqualTo(18);
        assertThat(study6.solvedCount()).isEqualTo(18);

        publisher.publishEvent(new AddSolvedCountEvent(this, 1L, 1, 1, 1, 1, 1, 1));

        assertThat(study1.solvedCount()).isEqualTo(12);
        assertThat(study2.solvedCount()).isEqualTo(12);
        assertThat(study3.solvedCount()).isEqualTo(12);
        assertThat(study4.solvedCount()).isEqualTo(18);
    }

    private Study createStudy(Long member, String name, String about, String leader) {
        Study study = studyService.create(new CreateReqDto(member, name, about, leader, 10));
        myStudyService.create(member, study);
        return study;
    }
}