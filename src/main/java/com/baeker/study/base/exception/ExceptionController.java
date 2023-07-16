package com.baeker.study.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
@Slf4j
public class ExceptionController {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(InvalidDuplicateException.class)
    public ResponseEntity<ErrorResponse> invalidDuplicateHandler(NotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NumberInputException.class)
    public ResponseEntity<ErrorResponse> numberInputExceptionHandler(NumberInputException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<ErrorResponse> numberInputExceptionHandler(NoPermissionException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(FORBIDDEN).body(new ErrorResponse(e.getMessage()));
    }
}
