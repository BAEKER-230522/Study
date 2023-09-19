package com.baeker.study.study.application.service;

import com.baeker.study.study.application.port.in.SnapshotUseCase;
import com.baeker.study.study.application.port.out.persistence.StudySnapshotRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.in.reqDto.BaekjoonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SnapshotService implements SnapshotUseCase {

    private final StudySnapshotRepositoryPort repository;

    @Override
    public void updateSnapshot(Study study, BaekjoonDto dto, String today) {
        List<StudySnapshot> snapshots = study.getSnapshots();

        if (isNew(today, snapshots))
            createSnapshot(study, dto, today);

        else
            modifySnapshot(snapshots.get(0), dto);
    }

    private boolean isNew(String today, List<StudySnapshot> snapshots) {
        return snapshots.get(0).getDayOfWeek().equals(today);
    }

    @Override
    public void createSnapshot(Study study, BaekjoonDto dto, String today) {
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

}
