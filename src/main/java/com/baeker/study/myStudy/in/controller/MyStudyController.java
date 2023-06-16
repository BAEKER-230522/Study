package com.baeker.study.myStudy.in.controller;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.myStudy.in.reqDto.AcceptDto;
import com.baeker.study.myStudy.in.reqDto.InviteMyStudyReqDto;
import com.baeker.study.myStudy.in.reqDto.JoinMyStudyReqDto;
import com.baeker.study.myStudy.in.reqDto.ModifyMsgDto;
import com.baeker.study.myStudy.in.resDto.CreateMyStudyDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/my-study")
@RequiredArgsConstructor
@Tag(name = "My Study",description = "My Study - CRUD")
public class MyStudyController {

    private final MyStudyService myStudyService;
    private final StudyService studyService;


    //-- 가입 신청 --//
    @PostMapping("/v1/join")
    @Operation(summary = "Study 에 가입 신청")
    public RsData join(@RequestBody @Valid JoinMyStudyReqDto dto) {
        log.info("study 가입신청 요청 확인 study id = {}", dto.getStudy());

        Study study = studyService.findById(dto.getStudy());
        MyStudy myStudy = myStudyService.join(dto, study);

        CreateMyStudyDto resDto = new CreateMyStudyDto(myStudy);

        log.info("가입 신청 완료 my study id = {}", resDto.getMyStudyId());
        return RsData.successOf(resDto);
    }

    //-- 초대 하기 --//
    @PostMapping("/v1/invite")
    @Operation(summary = "Study 로 초대")
    public RsData invite(@RequestBody @Valid InviteMyStudyReqDto dto) {
        log.info("study 로 가입 초대 study id = {}", dto.getStudy());

        Study study = studyService.findById(dto.getStudy());
        MyStudy myStudy = myStudyService.invite(dto, study);

        CreateMyStudyDto r = new CreateMyStudyDto(myStudy);

        log.info("초대 완료 my study id = {}", r.getMyStudyId());
        return RsData.successOf(r);
    }

    //-- 초대, 가입 메시지 수정 --//
    @PostMapping("/v1/msg")
    @Operation(summary = "가입, 초대 메시지 업데이트")
    public RsData modifyMsg(@RequestBody @Valid ModifyMsgDto dto) {
        log.info("메시지 수정 요청 확인 my study id = {}", dto.getId());

        MyStudy myStudy = myStudyService.findById(dto.getId());
        myStudyService.modifyMsg(myStudy, dto.getMsg());

        CreateMyStudyDto resDto = new CreateMyStudyDto(myStudy);

        log.info("msg 변경 완료 my study id = {} / msg = {}", resDto.getMyStudyId(), resDto.getMsg());
        return RsData.successOf(resDto);
    }

    //-- 초대, 가입 요청 거절 --//
    @DeleteMapping("/v1")
    @Operation(summary = "가입, 초대 거절")
    public RsData delete(@RequestParam Long id) {
        log.info("My Study 삭제 요청 확인 my study id = {}", id);

        MyStudy myStudy = myStudyService.findById(id);
        myStudyService.delete(myStudy);

        log.info("My Study 삭제 완료");
        return RsData.of("S-1", "My Study 삭제 완료");
    }

    //-- 가입 , 초대 승인 --//
    @PostMapping("/v1/accept")
    @Operation(summary = "가입, 초대 승인")
    public RsData accept(@RequestBody @Valid AcceptDto dto) {
        log.info("가입, 초대 승인 요청 확인 my study id ={}", dto.getId());

        MyStudy myStudy = myStudyService.findById(dto.getId());
        myStudyService.accept(myStudy);

        CreateMyStudyDto resDto = new CreateMyStudyDto(myStudy);

        log.info("가입, 초대 승인 완료 my study id = {} / msg = {}", resDto.getMyStudyId(), resDto.getMsg());
        return RsData.successOf(resDto);
    }
}
