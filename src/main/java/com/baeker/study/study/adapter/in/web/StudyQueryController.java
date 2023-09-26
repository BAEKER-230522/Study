package com.baeker.study.study.adapter.in.web;

import com.baeker.study.global.feign.dto.CandidateResDto;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.MemberResDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${custom.mapping.study.web}")
@RequiredArgsConstructor
public class StudyQueryController {

    private final StudyQueryUseCase studyQueryUseCase;

    @Operation(summary = "study id 로 study 조회")
    @GetMapping("/v2/{studyId}")
    public ResponseEntity<StudyResDto> findById(
            @PathVariable @Valid Long studyId
    ) {
        Study study = studyQueryUseCase.byId(studyId);
        StudyResDto dto = new StudyResDto(study);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "study 이름으로 study 조회")
    @GetMapping("/v2/{name}")
    public ResponseEntity<StudyResDto> findByName(
            @PathVariable @Valid String name
    ) {
        Study study = studyQueryUseCase.byName(name);
        StudyResDto dto = new StudyResDto(study);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "모든 study 목록 조회")
    @GetMapping("/v2/all")
    public ResponseEntity<List<StudyResDto>> findAll() {
        List<StudyResDto> resDtos = studyQueryUseCase.all();
        return ResponseEntity.ok(resDtos);
    }

    @Operation(summary = "member id 로 study 목록 조회")
    @GetMapping("/v2/{memberId}")
    public ResponseEntity<List<StudyResDto>> findByMemberId(
            @PathVariable @Valid Long memberId,
            @RequestParam @Valid int status
    ) {
        List<StudyResDto> dtoList = studyQueryUseCase.byMemberId(memberId, status);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(summary = "study 정회원 목록 조회")
    @GetMapping("/v2/member-list/{studyId}")
    public ResponseEntity<List<MemberResDto>> findMembers(
            @PathVariable @Valid Long studyId
    ) {
        Study study = studyQueryUseCase.byId(studyId);
        List<MemberResDto> dtoList = studyQueryUseCase.byMemberList(study);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(summary = "study 가입 대기 회원 목록 조회")
    @GetMapping("/v2/candidate-list/{studyId}")
    public ResponseEntity<CandidateResDto> findCandidates(
            @PathVariable @Valid Long studyId
    ) {
        Study study = studyQueryUseCase.byId(studyId);
        CandidateResDto resDto = studyQueryUseCase.byCandidateList(study);
        return ResponseEntity.ok(resDto);
    }

    @Operation(summary = "랭킹 순으로 study 목록 조회")
    @GetMapping("/v2/ranking")
    public ResponseEntity<List<StudyResDto>> orderByRanking(
            @RequestParam(defaultValue = "0") @Valid int page,
            @RequestParam(defaultValue = "10") @Valid int content
    ) {
        List<StudyResDto> dtoList = studyQueryUseCase.allOrderByRanking(page, content);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(summary = "검색어로 study 목록 조회")
    @GetMapping("v1/{input}")
    public ResponseEntity<List<StudyResDto>> findByInput(
            @PathVariable @Valid String input,
            @RequestParam(defaultValue = "0") @Valid int page,
            @RequestParam(defaultValue = "10") @Valid int content
    ) {
        List<StudyResDto> dtoList = studyQueryUseCase.byInput(input, page, content);
        return ResponseEntity.ok(dtoList);
    }
}
