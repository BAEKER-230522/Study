package com.baeker.study.myStudy.in.controller;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.myStudy.in.reqDto.MyStudyJoinReqDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/my-study")
@RequiredArgsConstructor
public class MyStudyController {

    private final MyStudyService myStudyService;
    private final StudyService studyService;


    //-- 가입 신청 --//
    @PostMapping("/v1/join")
    public RsData join(@RequestBody @Valid MyStudyJoinReqDto dto) {
        log.info("study 가입신청 요청 확인 study id = {}", dto.getStudy());

        Study study = studyService.findById(dto.getStudy());
        MyStudy myStudy = myStudyService.join(dto, study);
    }
}
