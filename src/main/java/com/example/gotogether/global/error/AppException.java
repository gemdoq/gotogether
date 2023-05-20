package com.example.gotogether.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppException extends RuntimeException {

    private ErrorCode errorCode;
    private String appExceptionMessage;

    public AppException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.appExceptionMessage = errorCode.getErrorCodeMessage();
    }

}
