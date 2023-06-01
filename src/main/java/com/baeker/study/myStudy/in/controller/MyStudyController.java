package com.baeker.study.myStudy.in.controller;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.myStudy.in.reqDto.InviteMyStudyReqDto;
import com.baeker.study.myStudy.in.reqDto.JoinMyStudyReqDto;
import com.baeker.study.myStudy.in.resDto.CreateMyStudyDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/my-study")
@RequiredArgsConstructor
public class MyStudyController {

    private final MyStudyService myStudyService;
    private final StudyService studyService;


    //-- 가입 신청 --//
    @PostMapping("/v1/join")
    public RsData join(@RequestBody @Valid JoinMyStudyReqDto dto) {
        log.info("study 가입신청 요청 확인 study id = {}", dto.getStudy());

        Study study = studyService.findById(dto.getStudy());
        MyStudy myStudy = myStudyService.join(dto, study);

        CreateMyStudyDto ResDto = new CreateMyStudyDto(myStudy);

        log.info("가입 신청 완료 my study id = {}", ResDto.getMyStudyId());
        return RsData.successOf(ResDto);
    }

    //-- 초대 하기 --//
    @PostMapping("/v1/invite")
    public RsData invite(@RequestParam @Valid InviteMyStudyReqDto dto) {
        log.info("study 로 가입 초대 study id = {}", dto.getStudy());

        Study study = studyService.findById(dto.getStudy());
        MyStudy myStudy = myStudyService.invite(dto, study);

        CreateMyStudyDto ResDto = new CreateMyStudyDto(myStudy);

        log.info("초대 완료 my study id = {}", ResDto.getMyStudyId());
        return RsData.successOf(ResDto);
    }
}
