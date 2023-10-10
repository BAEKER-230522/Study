package com.baeker.study.base.error.controller;

import com.baeker.study.base.error.*;
import com.baeker.study.base.error.exception.InvalidDuplicateException;
import com.baeker.study.base.error.exception.NoPermissionException;
import com.baeker.study.base.error.exception.NotFoundException;
import com.baeker.study.base.error.exception.NumberInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
@Slf4j
public class ExceptionController {


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
}
