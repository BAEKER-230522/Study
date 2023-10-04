package com.baeker.study.study.application.service;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.in.reqDto.BaekjoonDto;
import com.baeker.study.testUtil.service.study.SnapshotServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.baeker.study.testUtil.global.CreateDomain.createStudy;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("snapshot 최신화 단위 테스트")
@ExtendWith(MockitoExtension.class)
class SnapshotService_updateTest extends SnapshotServiceMock {

    @InjectMocks
    SnapshotService snapshotService;

    @BeforeEach
    void setup() {
        snapshotSaveMocking();
        snapshotDeleteMocking();
    }

    @Test
    @DisplayName("스냅샷 덮어쓰기 최신화")
    void no1() {
        Study study = createStudy(1L, 1L, "스터디");

        for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++)
            updateSnapshot(study, 0);

        int snapshotSize = study.getSnapshots().size();
        assertThat(snapshotSize).isEqualTo(1);
    }


    @Test
    @DisplayName("스냅샷 추가 최신화")
    void no2() {
        Study study = createStudy(1L, 1L, "스터디");

        for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++)
            updateSnapshot(study, dayOfWeek);

        int snapshotSize = study.getSnapshots().size();
        assertThat(snapshotSize).isEqualTo(7);
    }

    private void updateSnapshot(Study study, int addDate) {
        BaekjoonDto dto = new BaekjoonDto(study);
        snapshotService.updateSnapshot(study, dto, addDate);
    }

    @Test
    @DisplayName("8번째 스냅샷이 추가될 경우")
    void no3() {
        Study study = createStudy(1L, 1L, "스터디");
        List<StudySnapshot> snapshots = study.getSnapshots();

        for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++)
            updateSnapshot(study, dayOfWeek);

        String dayOfWeek1 = snapshots.get(0).getDayOfWeek();
        String yesterday = createDay(-1);
        assertThat(dayOfWeek1).isEqualTo(yesterday);

        updateSnapshot(study, 0);
        String dayOfWeek2 = snapshots.get(0).getDayOfWeek();
        String today = createDay(0);
        assertThat(dayOfWeek2).isEqualTo(today);
        assertThat(snapshots.size()).isEqualTo(7);
    }

    private String createDay(int addDate) {
        return LocalDateTime.now().plusDays(addDate).getDayOfWeek().toString();
    }
}