package com.baeker.study.global.exception;

import com.baeker.study.global.exception.feign.FeignClientException;
import com.baeker.study.global.exception.feign.FeignException;
import com.baeker.study.global.exception.feign.FeignServerException;
import com.baeker.study.global.exception.jwt.InvalidJwtException;
import com.baeker.study.global.exception.service.InvalidDuplicateException;
import com.baeker.study.global.exception.service.NoPermissionException;
import com.baeker.study.global.exception.service.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ErrorMsg> invalidJwtExceptionHandler(InvalidJwtException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMsg(e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMsg> notFoundExceptionHandler(NotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMsg(e.getMessage()));
    }

    @ExceptionHandler(InvalidDuplicateException.class)
    public ResponseEntity<ErrorMsg> invalidDuplicateHandler(NotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMsg(e.getMessage()));
    }

    @ExceptionHandler(NumberInputException.class)
    public ResponseEntity<ErrorMsg> numberInputExceptionHandler(NumberInputException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMsg(e.getMessage()));
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<ErrorMsg> numberInputExceptionHandler(NoPermissionException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(FORBIDDEN).body(new ErrorMsg(e.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMsg> feignException(FeignException e) {
        log.error(e.getMessage(), e.getStatus());
        return ResponseEntity.status(e.getStatus()).body(new ErrorMsg(e.getMessage()));
    }

    @ExceptionHandler(FeignServerException.class)
    public ResponseEntity<ErrorMsg> feignServerException(FeignServerException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(500).body(new ErrorMsg(e.getMessage()));
    }

    @ExceptionHandler(FeignClientException.class)
    public ResponseEntity<ErrorMsg> feignClientException(FeignClientException e) {
        log.error(e.getMessage(), e.getStatus());
        return ResponseEntity.status(e.getStatus()).body(new ErrorMsg(e.getMessage()));
    }
}