package com.baeker.study.domain.studyRule.api;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.domain.studyRule.dto.StudyRuleDto;
import com.baeker.study.domain.studyRule.dto.request.CreateStudyRuleRequest;
import com.baeker.study.domain.studyRule.dto.request.ModifyStudyRuleRequest;
import com.baeker.study.domain.studyRule.dto.response.CreateStudyRuleResponse;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.service.StudyRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/studyrule")
@RequiredArgsConstructor
public class StudyRuleController {
    private final StudyRuleService studyRuleService;

    /**
     * 생성
     * @param request
     * @return
     */
    // Path 로 입력받을지? 아니면 RequestBody 로 입력받을지?
    @PostMapping("/v1/studyrules")
    @Operation(summary = "스터디규칙 생성", description = "StudyRule 생성", tags = "StudyRule-생성")
    public RsData<CreateStudyRuleResponse> createStudyRule(@RequestBody @Valid CreateStudyRuleRequest request) {
        Long studyRuleId = studyRuleService.create(request);
        CreateStudyRuleResponse createStudyRuleResponse = new CreateStudyRuleResponse();
        createStudyRuleResponse.setId(studyRuleId);

        return RsData.successOf(createStudyRuleResponse);
    }

    /**
     * 수정
     */
    @PutMapping("/v1/{studyruleid}")
    @Operation(summary = "스터디 규칙 전부 변경", description = "body 에 이름(name), 소개(about), 규칙(ruleId) 입력", tags = "StudyRule-수정")
    public RsData<StudyRuleDto> modifyStudyRule(@PathVariable("studyruleid") Long studyruleid,
                                                           @RequestBody @Valid ModifyStudyRuleRequest request) {
        StudyRule studyRule = studyRuleService.getStudyRule(studyruleid);
        studyRuleService.modify(studyRule, request);
        return RsData.successOf(new StudyRuleDto(studyRule));
    }

    @PatchMapping("/v1/{studyruleid}")
    @Operation(summary = "스터디규칙 개별 변경", description = "이름(name), 소개(about), 규칙Id(ruleId) 원하는 값을 Key:Value 형태로 입력", tags = "StudyRule-수정")
    public RsData<StudyRuleDto> modifyStudyRule(@Parameter(description = "수정하고싶은 StudyRuleId 입력", example = "1")@PathVariable("studyruleid") Long studyruleid,
                                                @RequestBody Map<String, String> updates) {
        studyRuleService.updateStudyRule(studyruleid, updates);
        StudyRule studyRule = studyRuleService.getStudyRule(studyruleid);
        return RsData.successOf(new StudyRuleDto(studyRule));
    }

    /**
     * 조회
     */

    @GetMapping("/v1/search")
    @Operation(summary = "스터디 규칙 전체조회", description = "모든 StudyRule 을 조회 합니다.", tags = "StudyRule-조회")
    public RsData<List<StudyRuleDto>> searchStudyRule() {
        List<StudyRule> studyRuleList = studyRuleService.getAll();
        List<StudyRuleDto> collect = studyRuleList.stream()
                .map(StudyRuleDto::new)
                .toList();
        return RsData.successOf(collect);
    }

    @GetMapping("/v1/search/{studyruleid}")
    @Operation(summary = "스터디 규칙 개별조회", description = "한개씩 조회합니다.", tags = "StudyRule-조회")
    public RsData<StudyRuleDto> searchStudyRuleId(@Parameter(description = "조회 하고싶은 StudyRuleId 입력", example = "1") @PathVariable("studyruleid") Long studyruleid) {
        StudyRule studyRule = studyRuleService.getStudyRule(studyruleid);
        return RsData.of("S-1", String.format("%d 번 아이디 조회 결과 입니다.", studyruleid), new StudyRuleDto(studyRule));
    }

    @DeleteMapping("/v1/studyrules/{studyruleid}")
    @Operation(summary = "스터디 규칙 삭제", description = "스터디 규칙을 삭제합니다.", tags = "StudyRule-삭제")
    public RsData delete(@Parameter(description = "삭제하고싶은 StudyRuleId 입력", example = "1")@PathVariable("studyruleid") Long studyruleid) {
        StudyRule studyRule = studyRuleService.getStudyRule(studyruleid);
        studyRuleService.delete(studyRule);
        return RsData.of("S-1", String.format("%d 번 아이디가 삭제 되었습니다.", studyruleid), studyruleid);
    }

}
