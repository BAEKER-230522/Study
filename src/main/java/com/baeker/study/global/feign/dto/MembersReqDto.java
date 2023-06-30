package com.baeker.study.global.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MembersReqDto {

    private List<Long> members;
}
