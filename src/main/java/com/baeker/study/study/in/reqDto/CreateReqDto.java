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
    @Schema(example = "스터디 리더")
    private String leader;
    @Schema(example = "10")
    private Integer capacity;

    public static  CreateReqDto createStudy(Long member, String name, String about, String leader, Integer capacity) {
        CreateReqDto dto = new CreateReqDto();
        dto.member = member;
        dto.name = name;
        dto.about = about;
        dto.leader = leader;
        dto.capacity = capacity;
        return dto;
    }
}
