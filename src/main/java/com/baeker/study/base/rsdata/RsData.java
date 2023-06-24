package com.baeker.study.base.rsdata;

import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class RsData<T> {

    private String resultCode;
    private String msg;
    private  T data;

    //-- create RsData --//
    public static <T> RsData<T> of(String resultCode, String msg, T data) {
        return new RsData<>(resultCode, msg, data);
    }

    public static <T> RsData<T> of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }



    public static <T> RsData<T> successOf(T data) {
        return of("S-1", "성공", data);
    }

    public static <T> RsData<T> failOf(T data) {
        return of("F-1", "실패", data);
    }



    //-- RsData Checker --//
    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }

    public boolean isFail() {
        return isSuccess() == false;
    }
}