package com.baeker.study.global.exception.service;

public class NoPermissionException extends RuntimeException {

    public NoPermissionException(String msg) {
        super(msg);
    }
}
