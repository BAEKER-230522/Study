package com.baeker.study.global.exception.feign;

public class FeignServerException extends RuntimeException{

    public FeignServerException(String message) {
        super("feign server exception : " + message);
    }
}
