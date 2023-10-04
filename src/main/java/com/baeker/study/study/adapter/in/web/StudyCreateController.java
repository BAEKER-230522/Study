package com.baeker.study.study.adapter.in.web;

import com.baeker.study.global.jwt.JwtDecrypt;
import com.baeker.study.study.adapter.in.reqDto.StudyCreateReqDto;
import com.baeker.study.study.application.port.in.StudyCreateUseCase;
import com.baeker.study.study.in.resDto.CreateResDto;
import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${custom.mapping.study.web}")
@RequiredArgsConstructor
public class StudyCreateController {

    private final StudyCreateUseCase studyCreateUseCase;
    private final JwtDecrypt decrypt;

    @Operation(summary = "스터디 생성")
    @PostMapping("/v2/study")
    public ResponseEntity<CreateResDto> create(
            @RequestHeader("Authorization") String token,
            @RequestBody StudyCreateReqDto dto
    ) {
        Long memberId = decrypt.getMemberId(token);
        CreateResDto resDto = studyCreateUseCase.study(memberId, dto);
        return ResponseEntity.ok(resDto);
    }
}
