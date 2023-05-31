package com.baeker.study.study.in.controller;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;
    private final MyStudyService myStudyService;

    //-- 스터디 생성 --//
    @PostMapping("/v1/create")
    public String create(@RequestBody @Valid CreateReqDto dto) {
        log.info("스터디 생성 요청 확인");

        Study study = studyService.create(dto);


    }
}
