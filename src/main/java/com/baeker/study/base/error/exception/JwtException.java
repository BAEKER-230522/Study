package com.baeker.study.base.error.exception;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
