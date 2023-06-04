package com.baeker.study.study.in.reqDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateReqDto {

    private Long member;
    private String name;
    private String about;
    private String leader;
    private Integer capacity;
}
