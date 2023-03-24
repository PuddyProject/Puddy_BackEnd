package com.team.puddy.global.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.team.puddy.global.config.auth.AuthConstants;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;
import com.team.puddy.global.error.exception.NotFoundException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtVerifier {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret-key}")
    public String SECRET;

    //토큰 검증 메서드
    public DecodedJWT verify(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET))
                    .build().verify(token);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.JWT_EXCEPTION);
        }
    }

    public void verifyRefreshToken(String account, String refreshToken) {
        Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findByAccount(account);
        findRefreshToken.ifPresentOrElse(findToken -> {
                    if (!findToken.equals(AuthConstants.TOKEN_PREFIX.getValue() + refreshToken))
                        throw new NotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
                },
                () -> {
                    throw new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
                });
    }

    public void expireRefreshToken(String account) {
        refreshTokenRepository.deleteByAccount(account);
    }

    public String parseAccount(String accessToken) {
        return JWT.decode(accessToken).getClaim("account").asString();
    }


}