package com.baeker.study.study.adapter.in.reqDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyModifyReqDto {

    private Long id;
    private String name;
    private String about;
    private Integer capacity;
}
