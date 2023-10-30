package com.baeker.study.global.exception;

import lombok.Getter;

@Getter
public enum ErrorResponse {
    NOT_FOUND_STUDY_RULE("해당 스터디 룰을 찾을 수 없습니다.", 404),
    JWT_INVALID_EXCEPTION("유효하지 않은 토큰입니다.", 401);
    private final String errorMsg;
    private final int status;

    ErrorResponse(String errorMsg, int status) {
        this.errorMsg = errorMsg;
        this.status = status;
    }
}
