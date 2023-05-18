package com.example.gotogether.domain.user.application;

import com.example.gotogether.domain.user.dao.UserRepository;
import com.example.gotogether.domain.user.dto.UserCreateRequest;
import com.example.gotogether.domain.user.entity.UserEntity;
import com.example.gotogether.global.error.AppException;
import com.example.gotogether.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 유저 생성
     * @param req 유저실명, 계정명, 비밀번호, 이메일, 전화번호
     * @return UUID
     */
    public UUID createUser(UserCreateRequest req) {
        // 요청에 담긴 이메일로 중복 체크
        log.info("유저 생성 요청의 이메일({})로 중복체크 하겠습니다.", req.getEmailAddress());
        duplicateCheck(req.getEmailAddress());
        log.info("{}이 중복되지 않습니다.", req.getEmailAddress());

        // DAO를 Entity로 변환
        log.info("유저 생성 요청을 유저 엔티티로 변환하겠습니다.");
        UserEntity reqUser = UserEntity.toEntity(req);

        // Entity를 JPA em에 등록하고 해당 Entity id를 savedUserId 저장
        log.info("유저 엔티티를 JPA em에 등록하겠습니다.");
        UUID savedUserId = userRepository.save(reqUser).getId();
        log.info("요청된 유저 정보 변경사항이 DB에 정상적으로 반영되었습니다.");

        // savedUserId를 반환
        log.info("DB에 반영된 User UUID를 반환합니다.");
        return savedUserId;
    }

    private void duplicateCheck(String emailAddress) {
        userRepository.findByEmailAddress(emailAddress).ifPresent(userEntity -> { throw new AppException(ErrorCode.DUPLICATED_USER);});
    }
}
