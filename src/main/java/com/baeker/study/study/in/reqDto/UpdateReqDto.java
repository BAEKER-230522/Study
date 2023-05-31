package com.baeker.study.study.in.reqDto;

import lombok.Data;

@Data
public class UpdateReqDto {

    private Long id;
    private String name;
    private String about;
    private Integer capacity;
}
