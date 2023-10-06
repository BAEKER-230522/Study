package com.baeker.study.myStudy.adapter.in.web;

import com.baeker.study.global.jwt.JwtDecrypt;
import com.baeker.study.myStudy.application.port.in.MyStudyModifyUseCase;
import com.baeker.study.myStudy.application.port.in.MyStudyQueryUseCase;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.adapter.in.reqDto.AcceptReqDto;
import com.baeker.study.study.adapter.in.reqDto.ModifyMsgReqDto;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${custom.mapping.my-study.web}")
@RequiredArgsConstructor
public class MyStudyModifyController {

    private final MyStudyModifyUseCase myStudyModifyUseCase;
    private final MyStudyQueryUseCase myStudyQueryUseCase;
    private final StudyQueryUseCase studyQueryUseCase;
    private final JwtDecrypt decrypt;

    @Operation(summary = "가입 요청, 초대 승인")
    @PatchMapping("/v2/accept")
    public ResponseEntity accept(
            @RequestHeader("Authorization") String token,
            @RequestBody AcceptReqDto dto
    ) {
        Long memberId = decrypt.getMemberId(token);
        MyStudy myStudy = getMyStudy(dto.getStudyId(), dto.getTargetMemberId());
        myStudyModifyUseCase.accept(memberId, myStudy);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "가입, 초대 메시지 수정")
    @PatchMapping("/v2/msg")
    public ResponseEntity modifyMsg(
            @RequestHeader("Authorization") String token,
            @RequestBody ModifyMsgReqDto dto
    ) {
        Long memberId = decrypt.getMemberId(token);
        MyStudy myStudy = getMyStudy(dto.getStudyId(), memberId);
        myStudyModifyUseCase.msg(memberId, myStudy, dto.getMsg());
        return ResponseEntity.noContent().build();
    }


    private MyStudy getMyStudy(Long studyId, Long memberId) {
        Study study = studyQueryUseCase.byId(studyId);
        return myStudyQueryUseCase.byStudyIdAndMemberId(memberId, study);
    }
}
