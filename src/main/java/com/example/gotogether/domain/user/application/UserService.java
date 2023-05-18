package com.example.gotogether.domain.user.application;

import com.example.gotogether.domain.user.dao.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}
