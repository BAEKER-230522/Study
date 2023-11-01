package com.baeker.study.study.legacy.out;

import com.baeker.study.study.domain.entity.StudySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnapshotRepository extends JpaRepository<StudySnapshot, Long> {
}
