package com.baeker.study.study.domain.service;

import com.baeker.study.base.exception.NoPermissionException;
import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.myStudy.in.reqDto.InviteMyStudyReqDto;
import com.baeker.study.myStudy.in.reqDto.JoinMyStudyReqDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.in.reqDto.AddXpReqDto;
import com.baeker.study.study.in.reqDto.BaekjoonDto;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.in.reqDto.DeleteStudyReqDto;
import com.baeker.study.study.in.resDto.MemberResDto;
import com.baeker.study.study.in.resDto.SolvedCountReqDto;
import com.baeker.study.study.in.resDto.StudyResDto;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class StudyServiceTest {

    @Autowired
    private StudyService studyService;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private MyStudyService myStudyService;
    @MockBean
    private MemberClient memberClient;

    @BeforeEach
    public void beforeEach() {
        when(memberClient.updateMyStudy(any()))
                .thenReturn(new RsData<>("S-1", "성공", null));

        when(memberClient.findById(any()))
                .thenReturn(new RsData<MemberResDto>("S-1", "성공", new MemberResDto(1L, "leader", "bk1234")));

        when(memberClient.findById(6L))
                .thenReturn(new RsData<MemberResDto>("S-1", "성공", new MemberResDto("leader")));

        when((memberClient.deleteMyStudy(any())))
                .thenReturn(new RsData<>("S-1", "성공", null));
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
        Study study1 = createStudy(1L, "study1", "about", "member1");
        Study study2 = createStudy(1L, "study2", "about", "member1");
        Study study3 = createStudy(1L, "study3", "about", "member1");
        Study study4 = createStudy(2L, "study4", "about", "member2");
        Study study5 = createStudy(2L, "study5", "about", "member2");
        Study study6 = createStudy(2L, "study6", "about", "member2");

        List<Study> all = studyService.findAll();
        assertThat(all.size()).isEqualTo(6);
        assertThat(study1.solvedCount()).isEqualTo(0);

        studyService.addSolveCount(new SolvedCountReqDto(1L, 1, 1, 1, 1, 1, 1));

        assertThat(study1.solvedCount()).isEqualTo(6);
        assertThat(study2.solvedCount()).isEqualTo(6);
        assertThat(study3.solvedCount()).isEqualTo(6);
        assertThat(study4.solvedCount()).isEqualTo(0);
        assertThat(study5.solvedCount()).isEqualTo(0);
        assertThat(study6.solvedCount()).isEqualTo(0);

        List<StudySnapshot> snapshots4 = study4.getSnapshots();
        assertThat(snapshots4.size()).isEqualTo(0);

        studyService.addSolveCount(new SolvedCountReqDto(2L, 3, 3, 3, 3, 3, 3));

        assertThat(study1.solvedCount()).isEqualTo(6);
        assertThat(study4.solvedCount()).isEqualTo(18);
        assertThat(study5.solvedCount()).isEqualTo(18);
        assertThat(study6.solvedCount()).isEqualTo(18);

        studyService.addSolveCount(new SolvedCountReqDto(1L, 1, 1, 1, 1, 1, 1));

        assertThat(study1.solvedCount()).isEqualTo(12);
        assertThat(study2.solvedCount()).isEqualTo(12);
        assertThat(study3.solvedCount()).isEqualTo(12);
        assertThat(study4.solvedCount()).isEqualTo(18);

        List<StudySnapshot> snapshots1 = study1.getSnapshots();
        String today = dayCalculator(0);

        assertThat(snapshots1.size()).isEqualTo(1);
        assertThat(snapshots1.get(0).getDayOfWeek()).isEqualTo(today);
        assertThat(snapshots1.get(0).solvedCount()).isEqualTo(12);
    }

    @Test
    @DisplayName("Snapshot 테스트")
    void no3() {
        Study study = createStudy(1L, "study", "about", "member");

        for (int i = 0; i < 7; i++)
            updateSnapshot(study, i);

        List<StudySnapshot> list1 = study.getSnapshots();
        assertThat(list1.size()).isEqualTo(7);

        String day = dayCalculator(0);
        assertThat(list1.get(0).getDayOfWeek()).isEqualTo(day);

        day = dayCalculator(-1);
        assertThat(list1.get(6).getDayOfWeek()).isEqualTo(day);

        updateSnapshot(study, 0);
        List<StudySnapshot> list2 = study.getSnapshots();
        assertThat(list2.size()).isEqualTo(7);

        day = dayCalculator(1);
        assertThat(list1.get(0).getDayOfWeek()).isEqualTo(day);

        day = dayCalculator(0);
        assertThat(list1.get(6).getDayOfWeek()).isEqualTo(day);
    }

    @Test
    @DisplayName("member 의 study 조회")
    public void no4() {
        Study study1 = createStudy(1L, "my study1", "", "1");
        Study study2 = createStudy(2L, "my study2", "", "2");
        Study study3 = createStudy(3L, "pending study", "", "3");
        Study study4 = createStudy(4L, "invite study", "", "4");
        Study study5 = createStudy(5L, "study", "", "5");

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

    @Test
    @DisplayName("백준 연동 안할경우 가입 금지")
    public void no5() {
        assertThatThrownBy(() -> createStudy(6L, "my study1", "", "1"))
                .isInstanceOf(NoPermissionException.class);

        Study study = createStudy(1L, "my study1", "", "1");

        Study findStudy = studyService.findById(study.getId());

        assertThat(findStudy.getName()).isEqualTo(study.getName());
    }

    @Test
    @DisplayName("검색어로 study 찾기")
    public void no7() {
        Study study1 = createStudy(1L, "abc", "about", "member1");
        Study study2 = createStudy(1L, "bcd", "about", "member1");
        Study study3 = createStudy(1L, "cde", "about", "member1");
        Study study4 = createStudy(1L, "def", "about", "member1");
        Study study5 = createStudy(1L, "efg", "about", "member1");
        Study study6 = createStudy(1L, "fgh", "about", "member1");

        List<StudyResDto> findByC = studyService.findByInput("c", 0, 10);
        assertThat(findByC.size()).isEqualTo(3);
        assertThat(findByC.get(0).getName()).isEqualTo(study1.getName());

        List<StudyResDto> findByCD = studyService.findByInput("cd", 0, 10);
        assertThat(findByCD.size()).isEqualTo(2);

        List<StudyResDto> findByA = studyService.findByInput("A", 0, 10);
        assertThat(findByA.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("ranking 확인")
    void no8() {
        for (int i = 0; i < 5; i++) {
            Study study = createStudy(1L, "study" + i, "about", "member1");
            AddXpReqDto dto = new AddXpReqDto();
            dto.setId(study.getId());
            dto.setXp(i * 10);
            studyService.addXp(dto);
        }

        List<StudyResDto> studies = studyService.findAllOrderByRanking(0, 5);
        for (StudyResDto study : studies)
            assertThat(study.getRanking()).isNull();

        studyService.updateRanking();

        studies = studyService.findAllOrderByRanking(0, 5);
        assertThat(studies.get(0).getRanking()).isEqualTo(1);
        assertThat(studies.get(1).getRanking()).isEqualTo(2);
        assertThat(studies.get(2).getRanking()).isEqualTo(3);
        assertThat(studies.get(3).getRanking()).isEqualTo(4);
        assertThat(studies.get(4).getRanking()).isEqualTo(5);
    }

    @Test
    @DisplayName("Study 삭제")
    public void no9() {
        Study study = createStudy(1L, "Study", "", null);
        Long studyId = study.getId();

        for (Long i = 2L; i < 6; i++)
            joinStudy(study, i);

        int size = study.getMyStudies().size();
        assertThat(size).isEqualTo(5);

        deleteStudy(studyId, 1L);

        assertThatThrownBy(() -> studyService.findById(studyId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("존재하지 않는 id 입니다.");

    }

    private void deleteStudy(Long studyId, Long memberId) {
        DeleteStudyReqDto dto = new DeleteStudyReqDto();
        dto.setStudyId(studyId);
        dto.setMemberId(memberId);
        studyService.delete(dto);
    }

    private void joinStudy(Study study, Long memberId) {
        JoinMyStudyReqDto dto = new JoinMyStudyReqDto(study.getId(), memberId, "");
        MyStudy myStudy = myStudyService.join(dto, study);
        myStudyService.accept(myStudy);
    }

    private Study createStudy(Long member, String name, String about, String leader) {
        MyStudy myStudy = studyService.create(CreateReqDto.createStudy(member, name, about, 10));
        Study study = myStudy.getStudy();
        return study;
    }

    // test 용 스냅샷 생성 //
    private void updateSnapshot(Study study, int addDay) {
        String today = dayCalculator(addDay);
        BaekjoonDto dto = new BaekjoonDto(study);
        studyService.updateSnapshotTest(study, dto, today);
    }

    String dayCalculator(int addDate) {
        return LocalDateTime.now().plusDays(addDate).getDayOfWeek().toString();
    }
}