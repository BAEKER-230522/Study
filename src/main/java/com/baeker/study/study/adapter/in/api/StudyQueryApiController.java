package com.baeker.study.study.adapter.in.api;

import com.baeker.study.myStudy.application.port.in.MyStudyQueryUseCase;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.StudyResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "STUDY - 서버간 통신")
@RestController
@RequestMapping("${custom.mapping.study.api}")
@RequiredArgsConstructor
public class StudyQueryApiController {

    private final StudyQueryUseCase studyQueryUseCase;
    private final MyStudyQueryUseCase myStudyQueryUseCase;

    @Operation(summary = "검색어로 study 목록 조회")
    @GetMapping("/v2/input/{input}")
    public ResponseEntity<List<StudyResDto>> findByInput(
            @PathVariable String input,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int content
    ) {
        List<StudyResDto> dtoList = studyQueryUseCase.byInput(input, page, content);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(summary = "스터디원의 정회원 여부 확인")
    @GetMapping("/v1/member/{memberId}/{studyId}")
    public ResponseEntity isMember(
            @PathVariable Long memberId,
            @PathVariable Long studyId
    ) {
        myStudyQueryUseCase.isMember(studyId, memberId);
        return ResponseEntity.notFound().build();
    }
}
