package com.baeker.study.study.application.port.in;

import org.springframework.http.ResponseEntity;

public interface StudyDeleteUseCase {

    ResponseEntity<Long> study(Long study);
}
