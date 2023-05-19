package com.example.gotogether.domain.user.dto;

import com.example.gotogether.domain.user.entity.UserEntity;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserCreateResponse {
    private UUID id;
    private String username;
    private String account;

    public UserCreateResponse(UUID id, String username, String account) {
        this.id = id;
        this.username = username;
        this.account = account;
    }

    public static UserCreateResponse toDto(UserEntity savedUser) {
        return new UserCreateResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getAccount()
        );
    }
}
