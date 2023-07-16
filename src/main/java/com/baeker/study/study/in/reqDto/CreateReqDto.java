package com.baeker.study.study.in.reqDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class CreateReqDto {

    @Schema(example = "1")
    private Long member;
    @Schema(example = "스터디 이름")
    private String name;
    @Schema(example = "스터디 소개")
    private String about;
    @Schema(example = "10")
    private Integer capacity;

    public static  CreateReqDto createStudy(Long member, String name, String about, Integer capacity) {
        CreateReqDto dto = new CreateReqDto();
        dto.member = member;
        dto.name = name;
        dto.about = about;
        dto.capacity = capacity;
        return dto;
    }
}
