package com.example.gotogether.domain.user.entity;

import com.example.gotogether.domain.user.dto.UserCreateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String account;
    private String password;
    private String emailAddress;
    private String phoneNumber;

    public UserEntity(String username, String account, String password, String emailAddress, String phoneNumber) {
    }

    public static UserEntity toEntity(UserCreateRequest req) {
        return new UserEntity(
                req.getUsername(),
                req.getAccount(),
                req.getPassword(),
                req.getEmailAddress(),
                req.getPhoneNumber()
        );
    }
}

