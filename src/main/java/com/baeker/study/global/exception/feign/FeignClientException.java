package com.baeker.study.global.exception.feign;

public class FeignClientException extends RuntimeException {

    private final int status;

    public FeignClientException(String message, int status) {
        super("feign server exception : " + message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
