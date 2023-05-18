package com.example.gotogether.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 기존 DB와 중복
    DUPLICATED_USER(HttpStatus.CONFLICT, "이미 등록된 유저입니다.");

    private HttpStatus httpStatus;
    private String errorMessage;

}
