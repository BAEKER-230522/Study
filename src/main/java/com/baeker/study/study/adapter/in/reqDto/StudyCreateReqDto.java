package com.baeker.study.study.adapter.in.reqDto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyCreateReqDto {

    private String name;
    private String about;
    private Integer capacity;
}
