package com.baeker.study.study.adapter.in.web;

import com.baeker.study.global.jwt.JwtDecrypt;
import com.baeker.study.study.application.port.in.StudyDeleteUseCase;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "STUDY")
@RestController
@RequestMapping("${custom.mapping.study.web_usr}")
@RequiredArgsConstructor
public class StudyDeleteController {

    private final StudyDeleteUseCase studyDeleteUseCase;
    private final StudyQueryUseCase studyQueryUseCase;
    private final JwtDecrypt decrypt;

    @Operation(summary = "스터디 삭제")
    @DeleteMapping("/v2/{studyId}")
    public ResponseEntity deleteStudy(
            @RequestHeader("Authorization") String token,
            @PathVariable Long studyId
    ) {
        Long memberId = decrypt.getMemberId(token);
        Study study = studyQueryUseCase.byId(studyId);
        studyDeleteUseCase.study(study, memberId);
        return ResponseEntity.noContent().build();
    }
}
