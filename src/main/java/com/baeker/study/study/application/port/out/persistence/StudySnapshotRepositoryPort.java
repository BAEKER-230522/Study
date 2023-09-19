package com.baeker.study.study.application.port.out.persistence;

import com.baeker.study.study.domain.entity.StudySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudySnapshotRepositoryPort extends JpaRepository<StudySnapshot, Long> {
}
