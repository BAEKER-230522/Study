package com.baeker.study.study.application.service;

import com.baeker.study.study.application.port.in.StudyDeleteUseCase;
import org.springframework.http.ResponseEntity;

public class StudyDeleteService implements StudyDeleteUseCase {
    @Override
    public ResponseEntity<Long> study(Long study) {
        return null;
    }
}
