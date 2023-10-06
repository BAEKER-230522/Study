package com.baeker.study.global.exception.feign;

public class FeignException extends RuntimeException {

    private final int status;

    public FeignException(String message, int status) {
        super("feign client exception : " + message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
