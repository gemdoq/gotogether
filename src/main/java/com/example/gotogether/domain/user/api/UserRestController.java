package com.example.gotogether.domain.user.api;

import com.example.gotogether.domain.user.application.UserService;
import com.example.gotogether.domain.user.dto.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("")
    public String userCreate(@RequestBody UserCreateRequest req) {
        log.info("유저 생성 요청이 들어왔습니다.");
        UUID userId = userService.createUser(req);
        log.info("유저 생성 요청이 처리되었습니다.");
        log.info("생성된 유저 id : {}", userId);
        return "유저 생성 성공, UUID : " + userId;
    }
}
