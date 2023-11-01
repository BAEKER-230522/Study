package com.baeker.study.myStudy.legacy.out;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyStudyRepository extends JpaRepository<MyStudy, Long> {
}
