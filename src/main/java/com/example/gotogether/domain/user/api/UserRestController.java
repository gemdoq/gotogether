package com.example.gotogether.domain.user.api;

import com.example.gotogether.domain.user.application.UserService;
import com.example.gotogether.domain.user.dto.UserCreateRequest;
import com.example.gotogether.domain.user.dto.UserCreateResponse;
import com.example.gotogether.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<Response<UserCreateResponse>> userCreate(@RequestBody UserCreateRequest req) {
        log.info("유저 생성 요청 컨트롤러 시작됩니다.");
        log.info("유저 생성 요청이 들어왔습니다.");
        UserCreateResponse res = userService.createUser(req);
        log.info("유저 생성 요청이 처리되었습니다.");
        log.info("유저 생성 요청 컨트롤러 종료됩니다.");
        return ResponseEntity.ok(Response.success(res));
    }
}
