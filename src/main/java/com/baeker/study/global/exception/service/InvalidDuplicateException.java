package com.baeker.study.global.exception.service;

public class InvalidDuplicateException extends RuntimeException {

    public InvalidDuplicateException(String msg) {
        super(msg);
    }
}
