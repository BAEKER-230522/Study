package com.baeker.study.domain.studyRule.api;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.domain.studyRule.dto.StudyRuleForm;
import com.baeker.study.domain.studyRule.dto.request.CreateStudyRuleRequest;
import com.baeker.study.domain.studyRule.dto.response.CreateStudyRuleResponse;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.service.StudyRuleService;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/studyrule")
@RequiredArgsConstructor
public class StudyRuleController {
    private final StudyRuleService studyRuleService;

    @PostMapping("/v1/studyrules")
    @Operation(summary = "스터디규칙 생성", description = "")
    public RsData<CreateStudyRuleResponse> createStudyRule(@RequestBody @Valid CreateStudyRuleRequest request) {
        RsData<StudyRule> studyRuleRsData = studyRuleService.create(request);
        CreateStudyRuleResponse createStudyRuleResponse = new CreateStudyRuleResponse();
        createStudyRuleResponse.setId(studyRuleRsData.getData().getId());
        return RsData.successOf(createStudyRuleResponse);
    }
}
