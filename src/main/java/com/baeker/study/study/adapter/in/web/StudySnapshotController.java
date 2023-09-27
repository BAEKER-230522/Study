package com.baeker.study.study.adapter.in.web;

import com.baeker.study.study.application.port.in.SnapshotUseCase;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.SnapshotResDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${custom.mapping.study.web}")
@RequiredArgsConstructor
public class StudySnapshotController {

    private final SnapshotUseCase snapshotUseCase;
    private final StudyQueryUseCase studyQueryUseCase;

    @Operation(summary = "한주간 Study Snapshot 목록 조회")
    @GetMapping("/v2/snapshots/{studyId}")
    public ResponseEntity<List<SnapshotResDto>> findSnapshotOfWeek(
            @PathVariable @Valid Long studyId
    ) {
        Study study = studyQueryUseCase.byId(studyId);
        List<SnapshotResDto> resDtos = snapshotUseCase.getSnapshotOfWeek(study);
        return ResponseEntity.ok(resDtos);
    }
}
