package com.baeker.study.myStudy.adapter.in.web;

import com.baeker.study.myStudy.application.port.in.MyStudyQueryUseCase;
import com.baeker.study.myStudy.legacy.in.resDto.MyStudyResDto;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "MY STUDY")
@RestController
@RequestMapping("${custom.mapping.my-study.web_pub}")
@RequiredArgsConstructor
public class MyStudyQueryController {

    private final MyStudyQueryUseCase myStudyQueryUseCase;
    private final StudyQueryUseCase studyQueryUseCase;

    @Operation(summary = "my study 조회")
    @GetMapping("/v2/{memberId}/{studyId}")
    public ResponseEntity<MyStudyResDto> find(
            @PathVariable Long memberId,
            @PathVariable Long studyId
    ) {
        Study study = studyQueryUseCase.byId(studyId);
        MyStudyResDto resDto = myStudyQueryUseCase.toDtoByStudyIdAndMemberId(memberId, study);
        return ResponseEntity.ok(resDto);
    }
}
