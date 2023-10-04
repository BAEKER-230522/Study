package com.baeker.study.myStudy.adapter.in.web;

import com.baeker.study.global.jwt.JwtDecrypt;
import com.baeker.study.myStudy.adapter.in.reqDto.DropReqDto;
import com.baeker.study.myStudy.application.port.in.MyStudyDeleteUseCase;
import com.baeker.study.myStudy.application.port.in.MyStudyQueryUseCase;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${custom.mapping.my-study.web}")
@RequiredArgsConstructor
public class MyStudyDeleteController {

    private final MyStudyDeleteUseCase myStudyDeleteUseCase;
    private final MyStudyQueryUseCase myStudyQueryUseCase;
    private final StudyQueryUseCase studyQueryUseCase;
    private final JwtDecrypt decrypt;

    @Operation(summary = "초대, 가입 요청 거절과 스터디 탈퇴")
    @DeleteMapping("/v2/{studyId}")
    public ResponseEntity delete(
            @RequestHeader("Authorization") String token,
            @PathVariable Long studyId
    ) {
        Long memberId = decrypt.getMemberId(token);
        MyStudy myStudy = getMyStudy(memberId, studyId);
        myStudyDeleteUseCase.myStudy(memberId, myStudy);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "스터디 회원 강퇴")
    @DeleteMapping("/v2/drop")
    public ResponseEntity drop(
            @RequestHeader("Authorization") String token,
            @RequestBody DropReqDto dto
    ) {
        Long memberId = decrypt.getMemberId(token);
        MyStudy myStudy = getMyStudy(dto.getDropMemberId(), dto.getStudyId());
        myStudyDeleteUseCase.dropOut(memberId, myStudy);
        return ResponseEntity.noContent().build();
    }


    private MyStudy getMyStudy(Long memberId, Long studyId) {
        Study study = studyQueryUseCase.byId(studyId);
        return myStudyQueryUseCase.byStudyIdAndMemberId(memberId, study);
    }
}
