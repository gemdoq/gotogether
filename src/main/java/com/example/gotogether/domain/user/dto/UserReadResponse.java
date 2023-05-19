package com.example.gotogether.domain.user.dto;

import com.example.gotogether.domain.user.entity.UserEntity;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserReadResponse {
    private UUID id;
    private String username;
    private String account;
    private String emailAddress;
    private String phoneNumber;

    public UserReadResponse(UUID id, String username, String account, String emailAddress, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.account = account;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public static UserReadResponse toDto(UserEntity foundUser) {
        return new UserReadResponse(
                foundUser.getId(),
                foundUser.getUsername(),
                foundUser.getAccount(),
                foundUser.getEmailAddress(),
                foundUser.getPhoneNumber()
        );
    }
}
