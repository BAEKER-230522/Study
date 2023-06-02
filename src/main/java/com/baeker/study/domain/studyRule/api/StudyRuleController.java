package com.baeker.study.domain.studyRule.api;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.domain.studyRule.dto.StudyRuleDto;
import com.baeker.study.domain.studyRule.dto.StudyRuleForm;
import com.baeker.study.domain.studyRule.dto.request.CreateStudyRuleRequest;
import com.baeker.study.domain.studyRule.dto.request.ModifyStudyRuleRequest;
import com.baeker.study.domain.studyRule.dto.response.CreateStudyRuleResponse;
import com.baeker.study.domain.studyRule.dto.response.ModifyStudyRuleResponse;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.service.StudyRuleService;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.in.resDto.StudyResDto;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostMapping("/v1/studyrules")
    @Operation(summary = "스터디규칙 생성", description = "")
    public RsData<CreateStudyRuleResponse> createStudyRule(@RequestBody @Valid CreateStudyRuleRequest request) {
        Long studyRuleId = studyRuleService.create(request);
        CreateStudyRuleResponse createStudyRuleResponse = new CreateStudyRuleResponse();
        createStudyRuleResponse.setId(studyRuleId);

        return RsData.successOf(createStudyRuleResponse);
    }

    /**
     * 수정
     */
    @PutMapping("/v1/{id}")
    @Operation(summary = "스터디 규칙 전부 변경", description = "body 에 이름(name), 소개(about), 규칙(ruleId) 입력")
    public RsData<ModifyStudyRuleResponse> modifyStudyRule(@PathVariable("id") Long id,
                                                           @RequestBody @Valid ModifyStudyRuleRequest request) {
        StudyRule studyRule = studyRuleService.getStudyRule(id);
        studyRuleService.modify(studyRule, request);
        return RsData.successOf(new ModifyStudyRuleResponse(id));
    }

    @PatchMapping("/v1/{id}")
    @Operation(summary = "스터디규칙 개별 변경", description = "이름(name), 소개(about), 규칙Id(ruleId)")
    public RsData<ModifyStudyRuleResponse> modifyStudyRule(@PathVariable("id") Long id, Map<String, String> updates) {
        studyRuleService.updateStudyRule(id, updates);
        return RsData.successOf(new ModifyStudyRuleResponse(id));
    }

    /**
     * 조회
     */

    @GetMapping("/v1/search")
    @Operation(summary = "스터디 규칙 전체조회")
    public RsData<List<StudyRuleDto>> searchStudyRule() {
        List<StudyRule> studyRuleList = studyRuleService.getAll();
        List<StudyRuleDto> collect = studyRuleList.stream()
                .map(StudyRuleDto::new)
                .toList();
        return RsData.successOf(collect);
    }

    @GetMapping("/v1/search/{id}")
    @Operation(summary = "스터디 규칙 개별조회", description = "")
    public RsData<StudyRuleDto> searchStudyRuleId(@PathVariable("id") Long id) {
        StudyRule studyRule = studyRuleService.getStudyRule(id);
        return RsData.of("S-1", String.format("%d 번 아이디 조회 결과 입니다.", id), new StudyRuleDto(studyRule));
    }


}
