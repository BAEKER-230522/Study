package com.baeker.study.myStudy.adapter.in.web;

import com.baeker.study.global.jwt.JwtDecrypt;
import com.baeker.study.global.dto.reqDto.InviteReqDto;
import com.baeker.study.global.dto.reqDto.JoinReqDto;
import com.baeker.study.myStudy.application.port.in.MyStudyCreateUseCase;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.legacy.in.resDto.CreateResDto;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "MY STUDY")
@RestController
@RequestMapping("${custom.mapping.my-study.web_usr}")
@RequiredArgsConstructor
public class MyStudyCreateController {

    private final MyStudyCreateUseCase myStudyCreateUseCase;
    private final StudyQueryUseCase studyQueryUseCase;
    private final JwtDecrypt decrypt;

    @Operation(summary = "Study 에 가입 요청")
    @PostMapping("/v2/join")
    public ResponseEntity<CreateResDto> join(
            @RequestHeader("Authorization") String token,
            @RequestBody JoinReqDto dto
    ) {
        Long memberId = decrypt.getMemberId(token);
        Study study = studyQueryUseCase.byId(dto.getStudyId());
        Long myStudyId = myStudyCreateUseCase.join(memberId, study, dto.getMsg());
        return ResponseEntity.ok(new CreateResDto(study.getId(), myStudyId));
    }

    @Operation(summary = "Study 로 초대하기")
    @PostMapping("/v2/invite")
    public ResponseEntity<CreateResDto> invite(
            @RequestHeader("Authorization") String token,
            @RequestBody InviteReqDto dto
    ) {
        Long memberId = decrypt.getMemberId(token);
        Study study = studyQueryUseCase.byId(dto.getStudyId());
        Long myStudyId = myStudyCreateUseCase.invite(memberId, study, dto);
        return ResponseEntity.ok(new CreateResDto(study.getId(), myStudyId));
    }
}
