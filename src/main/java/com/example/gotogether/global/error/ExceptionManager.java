package com.example.gotogether.global.error;

import com.example.gotogether.global.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ExceptionManager extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppException.class)
    private ResponseEntity<?> appException(AppException e) {
        log.info("AppException ErrorCode : {}", e.getErrorCode());
        log.info("HttpStatus : {}", e.getErrorCode().getHttpStatus());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(Response.error(e.getErrorCode()));
    }
}
