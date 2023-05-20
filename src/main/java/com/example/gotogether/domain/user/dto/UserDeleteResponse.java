package com.example.gotogether.domain.user.dto;

import com.example.gotogether.domain.user.entity.UserEntity;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserDeleteResponse {
    private UUID id;
    private String username;
    private String account;

    public UserDeleteResponse(UUID id, String username, String account) {
        this.id = id;
        this.username = username;
        this.account = account;
    }

    public static UserDeleteResponse toDto(UserEntity foundUser) {
        return new UserDeleteResponse(
                foundUser.getId(),
                foundUser.getUsername(),
                foundUser.getAccount()
        );
    }
}
