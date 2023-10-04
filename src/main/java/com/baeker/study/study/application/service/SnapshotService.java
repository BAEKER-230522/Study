package com.baeker.study.study.application.service;

import com.baeker.study.study.application.port.in.SnapshotUseCase;
import com.baeker.study.study.application.port.out.persistence.StudySnapshotRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.in.reqDto.BaekjoonDto;
import com.baeker.study.study.in.resDto.SnapshotResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SnapshotService implements SnapshotUseCase {

    private final StudySnapshotRepositoryPort repository;

    @Override
    public void updateSnapshot(Study study, BaekjoonDto dto, int addDate) {
        List<StudySnapshot> snapshots = study.getSnapshots();
        String today = LocalDate.now().plusDays(addDate).getDayOfWeek().toString();

        if (isNew(today, snapshots))
            createSnapshot(study, dto, addDate);

        else
            modifySnapshot(snapshots.get(0), dto);
    }

    private boolean isNew(String today, List<StudySnapshot> snapshots) {
        return snapshots.get(0).getDayOfWeek().equals(today);
    }

    @Override
    public void createSnapshot(Study study, BaekjoonDto dto, int addDate) {
        String today = LocalDate.now().plusDays(addDate).getDayOfWeek().toString();

        repository.save(
                StudySnapshot.create(study, dto, today)
        );

        List<StudySnapshot> snapshots = study.getSnapshots();

        if (snapshots.size() >= 8) {
            StudySnapshot snapshot = snapshots.get(7);
            snapshots.remove(snapshot);
            repository.delete(snapshot);
        }
    }

    private void modifySnapshot(StudySnapshot snapshot, BaekjoonDto dto) {
        repository.save(
                snapshot.modify(dto)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<SnapshotResDto> getSnapshotOfWeek(Study study) {
        List<StudySnapshot> snapshots = study.getSnapshots();
        return snapshots
                .stream()
                .map(s -> new SnapshotResDto(s))
                .toList();
    }

    @Override
    public void deleteStudy(Study study) {
        List<StudySnapshot> snapshots = study.getSnapshots();

        for (StudySnapshot snapshot : snapshots)
            repository.delete(snapshot);
    }
}
