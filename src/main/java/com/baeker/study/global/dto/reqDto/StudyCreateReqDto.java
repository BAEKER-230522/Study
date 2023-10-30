package com.baeker.study.global.dto.reqDto;

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
