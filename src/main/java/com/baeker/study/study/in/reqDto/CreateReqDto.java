package com.baeker.study.study.in.reqDto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class CreateReqDto {

    private Long member;
    private String name;
    private String about;
    private String leader;
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
