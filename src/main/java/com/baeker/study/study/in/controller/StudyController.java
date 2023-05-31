package com.baeker.study.study.in.controller;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.in.reqDto.UpdateLeaderReqDto;
import com.baeker.study.study.in.reqDto.UpdateReqDto;
import com.baeker.study.study.in.resDto.CreateResDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.baeker.study.study.in.resDto.UpdateResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;
    private final MyStudyService myStudyService;

    //-- create study --//
    @PostMapping("/v1/create")
    public RsData create(@RequestBody @Valid CreateReqDto dto) {
        log.info("스터디 생성 요청 확인");

        Study study = studyService.create(dto);
        MyStudy myStudy = myStudyService.create(dto.getMember(), study);

        log.info("스터디 생성 응답 완료 study id = {}", study.getId());
        return RsData.successOf(new CreateResDto(study.getId(), myStudy.getId()));
    }

    //-- update name, about, capacity --//
    @PostMapping("/v1/update")
    public RsData update(@RequestBody @Valid UpdateReqDto dto) {
        log.info("update 요청 확인 id = {}", dto.getId());

        Study study = studyService.update(dto);
        log.info("업데이트 완료");
        return RsData.successOf(new UpdateResDto(study.getId()));
    }

    //-- update leader --//
    @PostMapping("/v1/update-leader")
    public RsData updateLeader(@RequestBody @Valid UpdateLeaderReqDto dto) {
        log.info("리더 수정 요청 확인 id = {}", dto.getId());

        Study study = studyService.updateLeader(dto);
        UpdateResDto resDto = new UpdateResDto(study.getId());

        return RsData.successOf(resDto);
    }

    //-- find list --//
    @GetMapping("/v1/list")
    public RsData list(@RequestBody @Valid int page) {
        log.info("스터디 리스트 요청 확인 page = {}", page);

        Page<Study> studyPage = studyService.findAll(page);

        log.info("스터디 리스트 요청 완료");
        return RsData.successOf(studyPage);
    }

    //-- find by id --//
    @GetMapping("/v1/find-by-id")
    public RsData findByName(@RequestBody @Valid Long id) {
        log.info("Study 조회 요청 확인 id = {}", id);

        Study study = studyService.findById(id);
        StudyResDto resDto = new StudyResDto(study);

        log.info("Study 응답 완료 id = {}", resDto.getId());
        return RsData.successOf(resDto);
    }

    //-- find by name --//
    @GetMapping("/v1/find-by-name")
    public RsData findByName(@RequestBody @Valid String name) {
        log.info("Study 조회 요청 확인 name = {}", name);

        Study study = studyService.findByName(name);
        StudyResDto resDto = new StudyResDto(study);

        log.info("Study 응답 완료 id = {}", resDto.getId());
        return RsData.successOf(resDto);
    }

}
