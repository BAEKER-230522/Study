package com.baeker.study.global.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
        super(msg);
    }
}