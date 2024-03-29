package com.baeker.study.global.dto.reqDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyModifyReqDto {

    private Long studyId;
    private String name;
    private String about;
    private Integer capacity;
}
