package com.baeker.study.myStudy.application.port.out.persistence;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyStudyRepositoryPort extends JpaRepository<MyStudy, Long> {
}
