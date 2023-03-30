package com.team.puddy.domain.user.service;

import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.dto.request.LoginUserRequest;
import com.team.puddy.domain.user.dto.request.RegisterUserRequest;
import com.team.puddy.domain.user.dto.response.ResponseUserInfoDto;
import com.team.puddy.domain.user.dto.response.TokenReissueDto;
import com.team.puddy.domain.user.repository.UserQueryRepository;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.config.security.jwt.JwtVerifier;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.mapper.UserMapper;
import com.team.puddy.global.config.security.jwt.JwtTokenUtils;
import com.team.puddy.global.config.security.jwt.LoginToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.team.puddy.global.error.ErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final UserQueryRepository userQueryRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    private final JwtVerifier jwtVerifier;

    @Transactional
    public LoginToken login(LoginUserRequest request) {
        User user = userRepository.findByAccount(request.account()).orElseThrow(() -> new BusinessException(USER_NOT_FOUND, String.format("%s is not founded", request.account())));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성

        return jwtTokenUtils.createLoginToken(user);
    }

    @Transactional
    public void join(RegisterUserRequest request) {
        //회원가입된 유저가 있는지
        userRepository.findByAccount(request.account()).ifPresent(it -> {
            throw new BusinessException(ErrorCode.DUPLICATE_ACCOUNT, String.format("%s is duplicated", request.account()));
        });
        //회원가입 진행
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        //리턴

    }

    @Transactional
    public LoginToken reissueToken(TokenReissueDto tokenReissueDto) {
        String account = jwtVerifier.parseAccount(tokenReissueDto.accessToken());
        jwtVerifier.verifyRefreshToken(account, tokenReissueDto.refreshToken());
        User user = userRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return jwtTokenUtils.createLoginToken(user);
    }

    @Transactional(readOnly = true)
    public void duplicateEmailCheck(String email) {
        if (userQueryRepository.isExistsEmail(email)) {
            log.error("중복 에러 발생");
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

    }

    @Transactional(readOnly = true)
    public void duplicateAccountCheck(String account) {
        if (userQueryRepository.isExistsAccount(account)) {
            throw new BusinessException(ErrorCode.DUPLICATE_ACCOUNT);
        }
    }


    @Transactional
    public void logout(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        jwtVerifier.expireRefreshToken(user.getAccount());
    }
    @Transactional(readOnly = true)
    public ResponseUserInfoDto getUserInfo(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        return userMapper.toDto(findUser);
    }

    public void updateProfileImage(Long userId,String imagePath) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        findUser.setImagePath(imagePath);
    }

}
