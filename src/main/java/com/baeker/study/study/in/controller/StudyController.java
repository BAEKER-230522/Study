package com.baeker.study.study.in.controller;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.in.reqDto.AddXpReqDto;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.in.reqDto.UpdateLeaderReqDto;
import com.baeker.study.study.in.reqDto.UpdateReqDto;
import com.baeker.study.study.in.resDto.CreateResDto;
import com.baeker.study.study.in.resDto.SnapshotResDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.baeker.study.study.in.resDto.UpdateResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
@Tag(name = "study", description = "study crud")
public class StudyController {

    private final StudyService studyService;

    //-- create study --//
    @PostMapping("/v1/create")
    @Operation(summary = "Study 생성")
    public RsData<CreateResDto> create(@RequestBody @Valid CreateReqDto dto) {
        log.info("스터디 생성 요청 확인");

        MyStudy myStudy = studyService.create(dto);

        log.info("스터디 생성 응답 완료 study id = {}", myStudy.getStudy().getId());
        return RsData.successOf(new CreateResDto(myStudy.getStudy().getId(), myStudy.getId()));
    }

    //-- update name, about, capacity --//
    @PostMapping("/v1/update")
    @Operation(summary = "업데이트 name / about / capacity")
    public RsData<UpdateResDto> update(@RequestBody @Valid UpdateReqDto dto) {
        log.info("update 요청 확인 id = {}", dto.getId());

        Study study = studyService.update(dto);
        log.info("업데이트 완료");
        return RsData.successOf(new UpdateResDto(study.getId()));
    }

    //-- update leader --//
    @PostMapping("/v1/leader")
    @Operation(summary = "업데이트 leader")
    public RsData<UpdateResDto> updateLeader(@RequestBody @Valid UpdateLeaderReqDto dto) {
        log.info("리더 수정 요청 확인 id = {}", dto.getId());

        Study study = studyService.updateLeader(dto);
        UpdateResDto resDto = new UpdateResDto(study.getId());

        return RsData.successOf(resDto);
    }

    //-- add xp --//
    @PostMapping("/v1/xp")
    @Operation(summary = "XP 추가")
    public RsData<UpdateResDto> addXp(@RequestBody @Valid AddXpReqDto dto) {
        log.info("xp 추가 업데이트 요청 확인 id ={}", dto.getId());

        Study study = studyService.addXp(dto);
        UpdateResDto resDto = new UpdateResDto(study.getId());

        return RsData.successOf(resDto);
    }

    //-- find list --//
    @GetMapping("/v1/list")
    @Operation(summary = "모든 스터디 조회 + 페이징")
    public RsData<List<StudyResDto>> list(@RequestParam @Valid int page) {
        log.info("스터디 리스트 요청 확인 page = {}", page);

        List<StudyResDto> dtoList = studyService.findAll(page)
                .stream()
                .map(s -> new StudyResDto(s))
                .toList();

        log.info("스터디 리스트 요청 완료");
        return RsData.successOf(dtoList);
    }

    //-- find by id --//
    @GetMapping("/v1/id")
    @Operation(summary = "id 로 조회하기")
    public RsData<StudyResDto> findByName(@RequestParam @Valid Long id) {
        log.info("Study 조회 요청 확인 id = {}", id);

        Study study = studyService.findById(id);
        StudyResDto dto = new StudyResDto(study);

        log.info("Study 응답 완료 id = {}", dto.getId());
        return RsData.successOf(dto);
    }

    //-- find by name --//
    @GetMapping("/v1/name")
    @Operation(summary = "name 으로 조회하기")
    public RsData<StudyResDto> findByName(@RequestParam @Valid String name) {
        log.info("Study 조회 요청 확인 name = {}", name);

        Study study = studyService.findByName(name);
        StudyResDto dto = new StudyResDto(study);

        log.info("Study 응답 완료 id = {}", dto.getId());
        return RsData.successOf(dto);
    }

    //-- find by Snapshot list --//
    @GetMapping("/v1/snapshots")
    @Operation(summary = "study id 로 snapshot list 조회하기")
    public RsData<List<SnapshotResDto>> findAllSnapshot(@RequestParam @Valid Long id) {
        log.info("Study Snapshot list 요청 확인 Study id = {}", id);

        Study study = studyService.findById(id);
        List<SnapshotResDto> resDtoList = studyService
                .findAllSnapshot(study)
                .stream()
                .map(s -> new SnapshotResDto(s))
                .toList();

        log.info("Study Snapshot list 응답 완료 count = {}", resDtoList.size());
        return RsData.of("S-1", "count - " + resDtoList.size(), resDtoList);
    }

    //-- find by member id --//
    @GetMapping("/v1/member/{id}")
    @Operation(summary = "member id 로 member 가 가입한 모든 study 조회하기")
    public RsData<List<StudyResDto>> findByMemberId(
            @PathVariable Long id
    ) {
        log.info("member 의 study list 요청 확인 member id = {}", id);

        List<StudyResDto> resDtoList = studyService.findByMember(id)
                .stream()
                .map(s -> new StudyResDto(s))
                .toList();

        log.info("member 의 study 목룍 응답 완료 count = {}", resDtoList.size());
        return RsData.of("S-1", "count - " + resDtoList.size(), resDtoList);
    }
}
