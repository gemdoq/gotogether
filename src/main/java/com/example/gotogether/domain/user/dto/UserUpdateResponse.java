package com.example.gotogether.domain.user.dto;

import com.example.gotogether.domain.user.entity.UserEntity;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserUpdateResponse {
    private UUID id;
    private String username;
    private String account;

    public UserUpdateResponse(UUID id, String username, String account) {
        this.id = id;
        this.username = username;
        this.account = account;
    }

    public static UserUpdateResponse toDto(UserEntity updatedUser) {
        return new UserUpdateResponse(
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getAccount()
        );
    }
}
