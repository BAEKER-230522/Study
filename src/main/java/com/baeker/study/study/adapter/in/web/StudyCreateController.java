package com.baeker.study.study.adapter.in.web;

import com.baeker.study.global.jwt.JwtDecrypt;
import com.baeker.study.myStudy.application.port.in.MyStudyCreateUseCase;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.application.port.in.StudyCreateUseCase;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.in.resDto.CreateResDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${custom.mapping.study.web}")
@RequiredArgsConstructor
public class StudyCreateController {

    private final StudyCreateUseCase studyCreateUseCase;
    private final MyStudyCreateUseCase myStudyCreateUseCase;
    private final JwtDecrypt decrypt;

    @Operation(summary = "스터디 생성")
    @PostMapping("/v2/study")
    public ResponseEntity create(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateReqDto dto
    ) {
        Long memberId = decrypt.getMemberId(token);
        CreateResDto resDto = createStudyAndMyStudy(memberId, dto);
        return ResponseEntity.ok(resDto);
    }

    @Transactional
    public CreateResDto createStudyAndMyStudy(Long memberId, CreateReqDto dto) {
        Study study = studyCreateUseCase.study(memberId, dto);
        MyStudy myStudy = myStudyCreateUseCase.myStudy(memberId, study);
        return new CreateResDto(study.getId(), myStudy.getId());
    }
}
