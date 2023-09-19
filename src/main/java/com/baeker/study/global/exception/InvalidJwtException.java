package com.baeker.study.global.exception;

public class InvalidJwtException extends RuntimeException {

    public InvalidJwtException(String message) {
        super(message);
    }
}
