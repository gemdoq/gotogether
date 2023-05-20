package com.example.gotogether.domain.user.api;

import com.example.gotogether.domain.user.application.UserService;
import com.example.gotogether.domain.user.dto.*;
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
        log.info("유저 생성 요청 컨트롤러 시작합니다.");
        log.info("유저 생성 요청 들어왔습니다.");
        UserCreateResponse res = userService.createUser(req);
        log.info("유저 생성 요청 처리했습니다.");
        log.info("유저 생성 요청 컨트롤러 종료합니다.");
        return ResponseEntity.ok(Response.success(res));
    }

    @GetMapping("/{account}")
    public ResponseEntity<Response<UserReadResponse>> userRead(@PathVariable String account) {
        log.info("유저 조회 요청 컨트롤러 시작합니다.");
        log.info("유저 조회 요청 들어왔습니다.");
        UserReadResponse res = userService.readUserByAccount(account);
        log.info("유저 조회 요청 처리했습니다.");
        log.info("유저 조회 요청 컨트롤러 종료합니다.");
        return ResponseEntity.ok(Response.success(res));
    }

    @PutMapping("/{account}")
    public ResponseEntity<Response<UserUpdateResponse>> userUpdate(@PathVariable String account, @RequestBody UserUpdateRequest req) {
        log.info("유저 수정 요청 컨트롤러 시작합니다.");
        log.info("유저 수정 요청 들어왔습니다.");
        UserUpdateResponse res = userService.updateUser(account, req);
        log.info("유저 수정 요청 처리했습니다.");
        log.info("유저 수정 요청 컨트롤러 종료합니다.");
        return ResponseEntity.ok(Response.success(res));
    }
}
