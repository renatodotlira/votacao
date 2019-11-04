package com.poll.exceptions;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = FeignException.class)
    public ResponseEntity handleFeignException(FeignException ex) {
        if(ex.status() == 404)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found.");
        return ResponseEntity.status(HttpStatus.resolve(500)).body("");
    }

}