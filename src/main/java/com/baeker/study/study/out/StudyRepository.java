package com.baeker.study.study.out;

import com.baeker.study.study.domain.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
