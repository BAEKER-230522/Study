package com.baeker.study.global.feign.dto;

public record MemberDto(
        Long id,
        int bronze,
        int silver,
        int gold,
        int platinum,
        int diamond,
        int ruby,
        String baekJoonName,
        int solvedBaekJoon,
        String email,
        String username,
        boolean newMember
) {
}
