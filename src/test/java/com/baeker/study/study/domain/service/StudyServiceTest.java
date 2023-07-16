package com.baeker.study.study.domain.service;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.myStudy.in.reqDto.InviteMyStudyReqDto;
import com.baeker.study.myStudy.in.reqDto.JoinMyStudyReqDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.in.event.AddSolvedCountEvent;
import com.baeker.study.study.in.reqDto.BaekjoonDto;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.in.resDto.MemberResDto;
import com.baeker.study.study.out.SnapshotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class StudyServiceTest {

    @Autowired private StudyService studyService;
    @Autowired private ApplicationEventPublisher publisher;
    @Autowired private SnapshotRepository snapshotRepository;
    @Autowired private MyStudyService myStudyService;
    @MockBean private MemberClient memberClient;

    @BeforeEach
    public void beforeEach() {
        when(memberClient.updateMyStudy(any()))
                .thenReturn(new RsData<>("S-1", "성공", null));

        when(memberClient.findById(any()))
                .thenReturn(new RsData<MemberResDto>("S-1", "성공", new MemberResDto("leader")));
    }

    @Test
    @DisplayName("mockito test")
    void no0() {
        MyStudy myStudy = studyService.create(CreateReqDto.createStudy(1L, "name", "about", 10));
        Study study = studyService.findById(myStudy.getStudy().getId());

        assertThat(study.getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("이벤트로 해결한 문제 업데이트")
    void no1() {
        Study study1 = study(1L, "study1", "about", "member1");
        Study study2 = study(1L, "study2", "about", "member1");
        Study study3 = study(1L, "study3", "about", "member1");
        Study study4 = study(2L, "study4", "about", "member2");
        Study study5 = study(2L, "study5", "about", "member2");
        Study study6 = study(2L, "study6", "about", "member2");

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
    @DisplayName("Snapshot 날짜별 저장")
    void no2() {
        Study study = study(1L, "study", "about", "member");
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
    @DisplayName("Snapshot 7일만 보관")
    void no3() {
        Study study = study(1L, "study", "about", "member");
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

    @Test
    @DisplayName("member 의 study 조회")
    public void no4() {
        Study study1 = study(1L, "my study1", "", "1");
        Study study2 = study(2L, "my study2", "", "2");
        Study study3 = study(3L, "pending study", "", "3");
        Study study4 = study(4L, "invite study", "", "4");
        Study study5 = study(5L, "study", "", "5");

        MyStudy join = myStudyService.join(new JoinMyStudyReqDto(study2.getId(), 1L, "가입신청"), study2);
        myStudyService.accept(join);

        myStudyService.join(new JoinMyStudyReqDto(study3.getId(), 1L, "가입신청"), study3);
        myStudyService.invite(new InviteMyStudyReqDto(study4.getId(), 4L, 1L, "초대"), study4);

        List<Study> studyList = studyService.findByMember(1L, 1);
        List<Study> pendingList = studyService.findByMember(1L, 2);
        List<Study> inviteList = studyService.findByMember(1L, 3);

        assertThat(pendingList.size()).isEqualTo(1);
        assertThat(inviteList.size()).isEqualTo(1);
        assertThat(studyList.size()).isEqualTo(2);
    }




    private Study study(Long member, String name, String about, String leader) {
        MyStudy myStudy = studyService.create(CreateReqDto.createStudy(member, name, about, 10));
        Study study = myStudy.getStudy();
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