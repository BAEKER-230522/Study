package com.baeker.study.study.domain.service;

import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.in.event.AddSolvedCountEvent;
import com.baeker.study.study.in.reqDto.BaekjoonDto;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.out.SnapshotRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StudyServiceTest {

    @Autowired private StudyService studyService;
    @Autowired private MyStudyService myStudyService;
    @Autowired private ApplicationEventPublisher publisher;
    @Autowired private SnapshotRepository snapshotRepository;


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

        List<StudySnapshot> snapshots4 = study4.getSnapshots();
        assertThat(snapshots4.size()).isEqualTo(0);

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

        List<StudySnapshot> snapshots1 = study1.getSnapshots();
        String today = LocalDateTime.now().getDayOfWeek().toString();

        assertThat(snapshots1.size()).isEqualTo(1);
        assertThat(snapshots1.get(0).getDayOfWeek()).isEqualTo(today);
        assertThat(snapshots1.get(0).solvedCount()).isEqualTo(12);
    }

    @Test
    void Snapshot_날짜별_저장() {
        Study study = createStudy(1L, "study", "about", "member");
        Study findStudy = studyService.findById(study.getId());

        for (int i = 6; i > 0; i--)testSnapshot(findStudy, i);
        publisher.publishEvent(new AddSolvedCountEvent(this, 1L, 1, 1, 1, 1, 1, 1));

        assertThat(
                studyService.findAllSnapshot(findStudy).size())
                .isEqualTo(7);

        List<StudySnapshot> snapshots = findStudy.getSnapshots();

        for (StudySnapshot snapshot : snapshots)
            System.out.println(snapshot.getDayOfWeek());
    }

    @Test
    void Snapshot_7일만_data_보관() {
        Study study = createStudy(1L, "study", "about", "member");
        Study findStudy = studyService.findById(study.getId());
        for (int i = 7; i > 0; i--) testSnapshot(findStudy, i);

        assertThat(
                studyService.findAllSnapshot(findStudy).size())
                .isEqualTo(7);

        publisher.publishEvent(new AddSolvedCountEvent(this, 1L, 1, 1, 1, 1, 1, 1));
        assertThat(
                studyService.findAllSnapshot(findStudy).size())
                .isEqualTo(7);

        List<StudySnapshot> snapshots = findStudy.getSnapshots();
        String today = LocalDateTime.now().getDayOfWeek().toString();

        assertThat(snapshots.get(0).getDayOfWeek()).isEqualTo(today);
    }

    private Study createStudy(Long member, String name, String about, String leader) {
        Study study = studyService.create(CreateReqDto.createStudy(member, name, about, leader, 10));
        myStudyService.create(member, study);
        return study;
    }

    // test 용 스냅샷 생성 //
    private void testSnapshot(Study study, int lastDay) {
        String day = LocalDateTime.now().minusDays(lastDay).getDayOfWeek().toString();
        BaekjoonDto dto = new BaekjoonDto(study.getId(), 1, 1, 1, 1, 1, 1);
        StudySnapshot snapshot = StudySnapshot.create(study, dto, day);
        snapshotRepository.save(snapshot);
    }
}