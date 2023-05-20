package com.example.gotogether.domain.user.application;

import com.example.gotogether.domain.user.dao.UserRepository;
import com.example.gotogether.domain.user.dto.*;
import com.example.gotogether.domain.user.entity.UserEntity;
import com.example.gotogether.global.error.AppException;
import com.example.gotogether.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 유저 생성
     * @param req 유저실명, 계정명, 비밀번호, 이메일, 전화번호
     * @return UserCreateResponse
     */
    @Transactional
    public UserCreateResponse createUser(UserCreateRequest req) {
        log.info("유저 생성 요청 서비스 시작합니다.");

        // 요청에 담긴 이메일로 중복 체크
        log.info("유저 생성 요청의 이메일({}) 중복체크 하겠습니다.", req.getEmailAddress());
        duplicateCheck(req.getEmailAddress());
        log.info("{}이 중복되지 않습니다.", req.getEmailAddress());

        // DAO를 Entity로 변환
        log.info("유저 생성 요청 유저 엔티티 변환하겠습니다.");
        UserEntity reqUser = UserEntity.toEntity(req);
        log.info("유저 생성 요청 유저 엔티티 변환했습니다.");

        // Entity를 JPA em에 등록하고 해당 Entity를 savedUser 저장
        log.info("유저 엔티티 JPA em 등록합니다.");
        UserEntity savedUser = userRepository.save(reqUser);
        log.info("요청된 유저 정보 변경사항 DB 정상 반영했습니다.");

        // Entity를 savedUserDto로 변환
        log.info("반영된 유저 정보 변경사항 DTO 변환합니다.");
        UserCreateResponse savedUserDto = UserCreateResponse.toDto(savedUser);
        log.info("반영된 유저 정보 변경사항 DTO 변환했습니다.");

        log.info("유저 생성 요청 서비스 종료합니다.");
        return savedUserDto;
    }

    /**
     * 계정명으로 유저 조회
     * @param account 계정명
     * @return UserReadResponse
     */
    public UserReadResponse readUserByAccount(String account) {
        log.info("유저 조회 요청 서비스 시작합니다.");

        // 요청에 담긴 계정명으로 조회
        log.info("유저 조회 요청 계정명({}) DB 조회합니다.", account);
        UserEntity foundUser = findUserByAccount(account);
        log.info("계정명({}) 유저 조회 성공했습니다.", account);

        // Entity를 foundUserDto로 변환
        log.info("조회된 유저 정보 DTO 변환합니다.");
        UserReadResponse foundUserDto = UserReadResponse.toDto(foundUser);
        log.info("조회된 유저 정보 DTO 변환했습니다.");

        log.info("유저 조회 요청 서비스 종료합니다.");
        return foundUserDto;
    }

    /**
     * 유저 수정
     * @param account 계정명
     * @param req 유저실명, 계정명, 비밀번호, 이메일, 전화번호
     * @return UserUpdateResponse
     */
    @Transactional
    public UserUpdateResponse updateUser(String account, UserUpdateRequest req) {
        log.info("유저 수정 요청 서비스 시작합니다.");

        // 요청에 담긴 계정명으로 조회
        log.info("유저 수정 요청 계정명({}) DB 조회합니다.", account);
        UserEntity foundUser = findUserByAccount(account);
        log.info("계정명({}) 유저 조회 성공했습니다.", account);

        // 조회된 유저 수정
        log.info("조회된 유저 정보 수정합니다.");
        UserEntity updatedUser = foundUser.update(req);
        log.info("조회된 유저 정보 수정했습니다.");

        // 수정된 Entity를 foundUserDto로 변환
        log.info("수정된 유저 정보 DTO 변환합니다.");
        UserUpdateResponse updatedUserDto = UserUpdateResponse.toDto(updatedUser);
        log.info("수정된 유저 정보 DTO 변환했습니다.");

        log.info("유저 수정 요청 서비스 종료합니다.");
        return updatedUserDto;
    }

    private UserEntity findUserByAccount(String account) {
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, String.format("해당 계정명(%s)의 유저가 존재하지 않습니다.", account)));
    }

    private void duplicateCheck(String emailAddress) {
        userRepository.findByEmailAddress(emailAddress)
                .ifPresent(userEntity -> { throw new AppException(ErrorCode.DUPLICATED_USER);});
    }

}
