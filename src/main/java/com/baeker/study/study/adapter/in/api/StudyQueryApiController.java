package com.baeker.study.study.adapter.in.api;

import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.in.resDto.StudyResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "서버간 통신용 find study api.V2")
@RestController
@RequestMapping("${custom.mapping.study.api}")
@RequiredArgsConstructor
public class StudyQueryApiController {

    private final StudyQueryUseCase studyQueryUseCase;

    @Operation(summary = "검색어로 study 목록 조회")
    @GetMapping("v2/input/{input}")
    public ResponseEntity<List<StudyResDto>> findByInput(
            @PathVariable String input,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int content
    ) {
        List<StudyResDto> dtoList = studyQueryUseCase.byInput(input, page, content);
        return ResponseEntity.ok(dtoList);
    }
}
