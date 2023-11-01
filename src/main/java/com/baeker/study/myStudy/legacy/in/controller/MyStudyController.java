package com.baeker.study.myStudy.legacy.in.controller;

import com.baeker.study.global.legacy.rsdata.RsData;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.myStudy.in.reqDto.*;
import com.baeker.study.myStudy.legacy.in.reqDto.*;
import com.baeker.study.myStudy.legacy.in.resDto.CreateMyStudyDto;
import com.baeker.study.myStudy.legacy.in.resDto.MyStudyResDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.legacy.in.resDto.MemberResDto;
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
@Tag(name = "My Study", description = "My Study - CRUD")
public class MyStudyController {

    private final MyStudyService myStudyService;
    private final StudyService studyService;


    //-- find by member & study --//
    @GetMapping("/v1/{memberId}/{studyId}")
    @Operation(summary = "my study 조회")
    public RsData<MyStudyResDto> find(
            @PathVariable Long memberId,
            @PathVariable Long studyId
    ) {
        log.info("my study 조회 요청 확인 member id = {} / study id = {}", memberId, studyId);

        Study study = studyService.findById(studyId);
        MyStudy myStudy = myStudyService.duplicationCheck(memberId, study);
        MemberResDto member = myStudyService.findMemberByMemberId(memberId);
        MyStudyResDto resDto = new MyStudyResDto(myStudy, member, study);

        log.info("my study 응답 완료 my study id = {}", resDto.getId());
        return RsData.successOf(resDto);
    }

    //-- 가입 신청 --//
    @PostMapping("/v1/join")
    @Operation(summary = "Study 에 가입 신청")
    public RsData<CreateMyStudyDto> join(@RequestBody @Valid JoinMyStudyReqDto dto) {
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
    public RsData<CreateMyStudyDto> invite(@RequestBody @Valid InviteMyStudyReqDto dto) {
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
    public RsData<CreateMyStudyDto> modifyMsg(@RequestBody @Valid ModifyMsgDto dto) {
        log.info("메시지 수정 요청 확인 member id = {} / study id = {}", dto.getMemberId(), dto.getStudyId());

        Study study = studyService.findById(dto.getStudyId());
        MyStudy myStudy = myStudyService.duplicationCheck(dto.getMemberId(), study);
        myStudyService.modifyMsg(myStudy, dto.getMsg());

        CreateMyStudyDto resDto = new CreateMyStudyDto(myStudy);

        log.info("msg 변경 완료 my study id = {} / msg = {}", resDto.getMyStudyId(), resDto.getMsg());
        return RsData.successOf(resDto);
    }

    //-- 초대, 가입 요청 거절 --//
    @DeleteMapping("/v1")
    @Operation(summary = "가입, 초대 거절")
    public RsData delete(@RequestBody @Valid AcceptDto dto) {
        log.info("My Study 삭제 요청 확인 member id = {} / study id = {}", dto.getMemberId(), dto.getStudyId());

        Study study = studyService.findById(dto.getStudyId());
        MyStudy myStudy = myStudyService.duplicationCheck(dto.getMemberId(), study);
        myStudyService.delete(myStudy);

        log.info("My Study 삭제 완료");
        return RsData.of("S-1", "My Study 삭제 완료");
    }

    //-- 가입 , 초대 승인 --//
    @PostMapping("/v1/accept")
    @Operation(summary = "가입, 초대 승인")
    public RsData<CreateMyStudyDto> accept(@RequestBody @Valid AcceptDto dto) {
        log.info("가입, 초대 승인 요청 확인 member id = {} / study id ={}", dto.getMemberId(), dto.getStudyId());

        Study study = studyService.findById(dto.getStudyId());
        MyStudy myStudy = myStudyService.duplicationCheck(dto.getMemberId(), study);
        myStudyService.accept(myStudy);

        CreateMyStudyDto resDto = new CreateMyStudyDto(myStudy);

        log.info("가입, 초대 승인 완료 my study id = {} / msg = {}", resDto.getMyStudyId(), resDto.getMsg());
        return RsData.successOf(resDto);
    }

    //-- 강퇴 --//
    @DeleteMapping("/v1/drop")
    @Operation(summary = "스터디 회원 강퇴")
    public RsData drop(@RequestBody DropReqDto dto) {
        log.info("스터디 회원 강퇴 요청 확인 leader id = {} / study id = {} / drop member id = {}", dto.getLeaderId(), dto.getStudyId(), dto.getDropMemberId());

        Study study = studyService.findById(dto.getStudyId());
        myStudyService.dropOut(dto, study);

        log.info("스터디 회원 강퇴 완료 drop member id = {}", dto.getDropMemberId());
        return RsData.of("S-1", "성공");
    }

}
