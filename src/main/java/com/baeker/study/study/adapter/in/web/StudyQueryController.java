package com.baeker.study.study.adapter.in.web;

import com.baeker.study.global.feign.dto.CandidateResDto;
import com.baeker.study.study.application.port.in.SnapshotUseCase;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.MemberResDto;
import com.baeker.study.study.in.resDto.SnapshotResDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "find study api.V2", description = "스터디 조회 관련 api")
@RestController
@RequestMapping("${custom.mapping.study.web}")
@RequiredArgsConstructor
public class StudyQueryController {

    private final StudyQueryUseCase studyQueryUseCase;


    @Operation(summary = "study id 로 study 조회")
    @GetMapping("/v2/id/{studyId}")
    public ResponseEntity<StudyResDto> findById(
            @PathVariable Long studyId
    ) {
        Study study = studyQueryUseCase.byId(studyId);
        StudyResDto dto = new StudyResDto(study);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "study 이름으로 study 조회")
    @GetMapping("/v2/name/{name}")
    public ResponseEntity<StudyResDto> findByName(
            @PathVariable String name
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

    @Operation(summary = "member id 로 study 목록 조회",
            description =
                    "status = 1 : 정회원 \n" +
                            " 2 : 가입신청 목록 \n" +
                            " 3 : 초디받은 목록"
    )
    @GetMapping("/v2/study-list/{memberId}")
    public ResponseEntity<List<StudyResDto>> findByMemberId(
            @PathVariable Long memberId,
            @RequestParam int status
    ) {
        List<StudyResDto> dtoList = studyQueryUseCase.byMemberId(memberId, status);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(summary = "study 정회원 목록 조회")
    @GetMapping("/v2/member-list/{studyId}")
    public ResponseEntity<List<MemberResDto>> findMembers(
            @PathVariable Long studyId
    ) {
        Study study = studyQueryUseCase.byId(studyId);
        List<MemberResDto> dtoList = studyQueryUseCase.byMemberList(study);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(summary = "study 가입 대기 회원 목록 조회")
    @GetMapping("/v2/candidate-list/{studyId}")
    public ResponseEntity<CandidateResDto> findCandidates(
            @PathVariable Long studyId
    ) {
        Study study = studyQueryUseCase.byId(studyId);
        CandidateResDto resDto = studyQueryUseCase.byCandidateList(study);
        return ResponseEntity.ok(resDto);
    }

    @Operation(summary = "랭킹 순으로 study 목록 조회")
    @GetMapping("/v2/ranking")
    public ResponseEntity<List<StudyResDto>> orderByRanking(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int content
    ) {
        List<StudyResDto> dtoList = studyQueryUseCase.allOrderByRanking(page, content);
        return ResponseEntity.ok(dtoList);
    }
}
