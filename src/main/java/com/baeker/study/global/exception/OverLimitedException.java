package com.baeker.study.global.exception;

public class OverLimitedException extends RuntimeException {

    public OverLimitedException(String msg) {
        super(msg);
    }
}
