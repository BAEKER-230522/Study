package com.baeker.study.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 문제풀이수 = 오늘 - 어제
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private int bronze;
    private int silver;
    private int gold;
    private int platinum;
    private int diamond;
    private int ruby;
}
