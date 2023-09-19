package com.baeker.study.study.application.port.out.persistence;

import com.baeker.study.study.domain.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRepositoryPort extends JpaRepository<Study, Long> {
    Optional<Study> findByName(String name);
}
