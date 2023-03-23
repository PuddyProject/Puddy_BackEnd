package com.team.puddy.global.config.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.global.config.auth.AuthConstants;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenUtils {



    private RefreshTokenRepository refreshTokenRepository;
    private String SECRET;

    public JwtTokenUtils(@Value("${jwt.secret-key}") String SECRET,
                         RefreshTokenRepository refreshTokenRepository) {
        this.SECRET = SECRET;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public static long ACCESS_EXPIRATION_TIME = 60 * 60 * 1000L; // 1시간
    public static long REFRESH_EXPIRATION_TIME = 30 * 60 * 60 * 24 * 1000L; // 30일

    public LoginToken createLoginToken(User user) {
        String refreshToken = refreshToken(user);
        return LoginToken.builder()
                .accessToken(AuthConstants.TOKEN_PREFIX.getValue() + accessToken(user))
                .refreshToken(AuthConstants.TOKEN_PREFIX.getValue() + refreshToken)
                .build();
    }

    protected String accessToken(User user) {
        return JWT.create()
                .withSubject(user.getAccount())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .withClaim("id",user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("nickname", user.getNickname())
                .withClaim("email", user.getEmail())
                .withClaim("auth", user.getRole())
                .sign(Algorithm.HMAC256(SECRET));

    }

    protected String refreshToken(User user) {
        String token = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET));

        refreshTokenRepository.findByAccount(user.getAccount()).ifPresentOrElse(
                findToken -> {
                    findToken.setToken(token);
                    refreshTokenRepository.save(findToken);
                },
                () -> {
                    RefreshToken refreshToken = RefreshToken.builder()
                            .token(token)
                            .account(user.getAccount())
                            .build();
                    refreshTokenRepository.save(refreshToken);
                });
        return token;
}

}
