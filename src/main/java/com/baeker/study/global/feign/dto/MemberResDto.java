package com.baeker.study.global.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class MemberResDto {

    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    int bronze;
    int silver;
    int gold;
    int diamond;
    int ruby;
    int platinum;
    int solvedBaekJoon;
    private String username;
    private String nickname;
    private String baekJoonName;
    private String about;
    private String profileImg;
    private String kakaoProfileImage;
    private String provider;
    private String email;
    private String token;
    private boolean newMember;
}
