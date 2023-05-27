package com.example.gotogether.domain.user.application;

import com.example.gotogether.domain.user.dao.UserRepository;
import com.example.gotogether.domain.user.dto.*;
import com.example.gotogether.domain.user.entity.UserEntity;
import com.example.gotogether.global.error.AppException;
import com.example.gotogether.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        log.info("유저 생성 요청의 이메일({}) → 중복체크 하겠습니다.", req.getEmailAddress());
        duplicateCheck(req.getEmailAddress());
        log.info("{} → 중복되지 않습니다.", req.getEmailAddress());

        // DTO를 Entity로 변환
        log.info("유저 생성 요청 → 유저 엔티티 변환하겠습니다.");
        UserEntity reqUser = UserEntity.toEntity(req);
        log.info("유저 생성 요청 → 유저 엔티티 변환했습니다.");

        // Entity를 JPA em에 등록하고 해당 Entity를 savedUser 저장
        log.info("유저 엔티티 → JPA em 등록합니다.");
        UserEntity savedUser = userRepository.save(reqUser);
        log.info("요청된 유저 정보 변경사항 → DB 반영했습니다.");

        // Entity를 savedUserDto로 변환
        log.info("반영된 유저 정보 변경사항 → DTO 변환합니다.");
        UserCreateResponse savedUserDto = UserCreateResponse.toDto(savedUser);
        log.info("반영된 유저 정보 변경사항 → DTO 변환했습니다.");

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
        log.info("유저 조회 요청 계정명({}) → DB 조회합니다.", account);
        UserEntity foundUser = findUserByAccount(account);
        log.info("계정명({}) → 유저 조회 성공했습니다.", account);

        // Entity를 foundUserDto로 변환
        log.info("조회된 유저 정보 → DTO 변환합니다.");
        UserReadResponse foundUserDto = UserReadResponse.toDto(foundUser);
        log.info("조회된 유저 정보 → DTO 변환했습니다.");

        log.info("유저 조회 요청 서비스 종료합니다.");
        return foundUserDto;
    }

    public Page<UserReadResponse> readAllUser(PageRequest pageable) {
        log.info("모든 유저 조회 요청 서비스 시작합니다.");

        // Entity를 담은 Page를 DB 조회
        log.info("모든 유저 정보 페이지 → DB 조회합니다.");
        Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        log.info("모든 유저 정보 페이지 → DB 조회했습니다.");

        // Entity를 담은 Page를 DTO를 담은 List로 변환
        log.info("모든 유저 정보 페이지 → DTO 리스트 변환합니다.");
        List<UserReadResponse> dtoList = userEntityPage
                .getContent()
                .stream()
                .map(UserReadResponse::of)
                .collect(Collectors.toList());
        log.info("모든 유저 정보 페이지 → DTO 리스트 변환했습니다.");

        // DTO를 담은 List를 DTO를 담은 Page로 변환
        log.info("DTO 리스트 → DTO 페이지 변환합니다.");
        Page<UserReadResponse> dtoPage = new PageImpl<>(dtoList, pageable, userEntityPage.getTotalElements());
        log.info("DTO 리스트 → DTO 페이지 변환했습니다.");

        log.info("모든 유저 조회 요청 서비스 종료합니다.");
        return dtoPage;
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
        log.info("유저 수정 요청 계정명({}) → DB 조회합니다.", account);
        UserEntity foundUser = findUserByAccount(account);
        log.info("계정명({}) → 유저 조회 성공했습니다.", account);

        // 조회된 유저 수정
        log.info("조회된 유저 정보 → 수정합니다.");
        UserEntity updatedUser = foundUser.update(req);
        log.info("조회된 유저 정보 → 수정했습니다.");

        // 수정된 Entity를 foundUserDto로 변환
        log.info("수정된 유저 정보 → DTO 변환합니다.");
        UserUpdateResponse updatedUserDto = UserUpdateResponse.toDto(updatedUser);
        log.info("수정된 유저 정보 → DTO 변환했습니다.");

        log.info("유저 수정 요청 서비스 종료합니다.");
        return updatedUserDto;
    }

    /**
     * 유저 삭제
     * @param account 계정명
     * @return UserDeleteResponse
     */
    @Transactional
    public UserDeleteResponse deleteUser(String account) {
        log.info("유저 삭제 요청 서비스 시작합니다.");

        // 요청에 담긴 계정명으로 조회
        log.info("유저 삭제 요청 계정명({}) → DB 조회합니다.", account);
        UserEntity foundUser = findUserByAccount(account);
        log.info("계정명({}) → 유저 조회 성공했습니다.", account);

        // 조회된 유저 삭제
        log.info("조회된 유저 정보 → 삭제합니다.");
        userRepository.delete(foundUser);
        log.info("조회된 유저 정보 → 삭제했습니다.");

        // 삭제된 Entity를 foundUserDto로 변환
        log.info("삭제된 유저 정보 → DTO 변환합니다.");
        UserDeleteResponse deletedUserDto = UserDeleteResponse.toDto(foundUser);
        log.info("삭제된 유저 정보 → DTO 변환했습니다.");

        log.info("유저 삭제 요청 서비스 종료합니다.");
        return deletedUserDto;
    }

    /**
     * 계정명으로 유저 조회, 없을 시 AppException(USER_NOT_FOUND) 발생
     * @param account 계정명
     * @return UserEntity
     */
    private UserEntity findUserByAccount(String account) {
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, String.format("해당 계정명(%s)의 유저가 존재하지 않습니다.", account)));
    }

    /**
     * 이메일로 유저 중복 체크, 중복 시 AppException(DUPLICATED_USER) 발생
     * @param emailAddress
     */
    private void duplicateCheck(String emailAddress) {
        userRepository.findByEmailAddress(emailAddress)
                .ifPresent(userEntity -> { throw new AppException(ErrorCode.DUPLICATED_USER);});
    }
}
