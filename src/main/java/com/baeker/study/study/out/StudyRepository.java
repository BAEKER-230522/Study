package com.baeker.study.study.out;

import com.baeker.study.study.domain.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {

    Optional<Study> findByName(String name);
}
