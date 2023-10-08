package com.baeker.study.study.adapter.in.api;

import com.baeker.study.study.application.port.in.StudyModifyUseCase;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.AddXpReqDto;
import com.baeker.study.study.in.resDto.SolvedCountReqDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "서버간 통신용 modify study api.V2")
@RestController
@RequestMapping("${custom.mapping.study.api}")
@RequiredArgsConstructor
public class StudyModifyApiController {

    private final StudyModifyUseCase studyModifyUseCase;
    private final StudyQueryUseCase studyQueryUseCase;

    @Operation(summary = "XP 추가")
    @PatchMapping("/v2/xp")
    public ResponseEntity addXp(
            @RequestBody AddXpReqDto dto
    ) {
        Study study = studyQueryUseCase.byId(dto.getId());
        studyModifyUseCase.xp(study, dto.getXp());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "스터디의 해결한 문제 최신화",
            description = "Kafka 로 대체될 예정")
    @PatchMapping("/v2/solved")
    public ResponseEntity updateSolved(
            @RequestBody SolvedCountReqDto dto
    ) {
        List<StudyResDto> studtList = studyQueryUseCase.byMemberId(dto.getId(), 1);
        studyModifyUseCase.solvedCount(studtList, dto);
        return ResponseEntity.noContent().build();
    }
}
