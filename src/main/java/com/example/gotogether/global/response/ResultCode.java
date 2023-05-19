package com.example.gotogether.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {

    ERROR("ERROR"),
    SUCCESS("SUCCESS");

    private String result;

}
