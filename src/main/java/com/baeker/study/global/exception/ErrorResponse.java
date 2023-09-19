package com.baeker.study.global.exception;

import lombok.Getter;

@Getter
public enum ErrorResponse {
    NOT_FOUND_STUDY_RULE("해당 스터디 룰을 찾을 수 없습니다.", 404);

    private final String errorMsg;
    private final int status;

    ErrorResponse(String errorMsg, int status) {
        this.errorMsg = errorMsg;
        this.status = status;
    }
}
