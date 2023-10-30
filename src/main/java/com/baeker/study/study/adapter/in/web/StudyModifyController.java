package com.baeker.study.study.adapter.in.web;

import com.baeker.study.global.jwt.JwtDecrypt;
import com.baeker.study.global.dto.reqDto.StudyModifyReqDto;
import com.baeker.study.study.application.port.in.StudyModifyUseCase;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.UpdateLeaderReqDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.baeker.study.study.in.resDto.UpdateResDto;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "STUDY")
@RestController
@RequestMapping("${custom.mapping.study.web_usr}")
@RequiredArgsConstructor
public class StudyModifyController {

    private final StudyModifyUseCase studyModifyUseCase;
    private final StudyQueryUseCase studyQueryUseCase;
    private final JwtDecrypt decrypt;

    @Operation(summary = "스터디명, 소개, 최대 인원 수정")
    @PatchMapping("/v2/info")
    public ResponseEntity<UpdateResDto> update(
            @RequestHeader("Authorization") String token,
            @RequestBody StudyModifyReqDto dto
    ) {
        Long memberId = decrypt.getMemberId(token);
        Study study = studyQueryUseCase.byId(dto.getStudyId());
        UpdateResDto resDto = studyModifyUseCase.info(study, memberId, dto);
        return ResponseEntity.ok(resDto);
    }

    @Operation(summary = "스터디장 변경")
    @PatchMapping("/v2/leader")
    public ResponseEntity<StudyResDto> updateLeader(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateLeaderReqDto dto
    ) {
        Long memberId = decrypt.getMemberId(token);
        Study study = studyQueryUseCase.byId(dto.getStudyId());
        StudyResDto resDto = studyModifyUseCase.leader(study, memberId, dto);
        return ResponseEntity.ok(resDto);
    }
}
