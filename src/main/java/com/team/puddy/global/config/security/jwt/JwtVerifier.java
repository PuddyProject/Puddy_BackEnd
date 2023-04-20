package com.team.puddy.global.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;
import com.team.puddy.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtVerifier {

    private final RedisTemplate<String, String> redisTemplate;


    @Value("${jwt.secret-key}")
    public String SECRET;

    //토큰 검증 메서드
    public DecodedJWT verify(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.TOKEN_VERIFY_FAIL);
        }
    }
    public void verifyRefreshToken(String account, String refreshToken) {
        Optional<String> findRefreshToken = Optional.ofNullable(redisTemplate.opsForValue().get(account));
        findRefreshToken.ifPresentOrElse(findToken -> {
            if(!findToken.equals(refreshToken)) {
                throw new NotFoundException(ErrorCode.TOKEN_VERIFY_FAIL);
            }
        },
                () -> {
            throw new NotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
                });
    }

    public void expireRefreshToken(String account) {
          redisTemplate.delete(account);
    }

    public String parseAccount(String accessToken) {
        return JWT.decode(accessToken).getSubject();
    }


}
