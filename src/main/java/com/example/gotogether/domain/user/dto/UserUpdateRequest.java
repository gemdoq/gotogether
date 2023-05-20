package com.example.gotogether.domain.user.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private String username;
    private String account;
    private String password;
    private String emailAddress;
    private String phoneNumber;
}
