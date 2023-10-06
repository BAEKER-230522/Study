package com.baeker.study.global.exception.service;

public class OverLimitedException extends RuntimeException {

    public OverLimitedException(String msg) {
        super(msg);
    }
}
