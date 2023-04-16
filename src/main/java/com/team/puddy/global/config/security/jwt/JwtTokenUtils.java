package com.team.puddy.global.config.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.global.config.auth.AuthConstants;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtTokenUtils {

    private final RedisTemplate<String, String> redisTemplate;


    private final String SECRET;

    public JwtTokenUtils(@Value("${jwt.secret-key}") String SECRET,
                         RedisTemplate<String,String> redisTemplate) {
        this.SECRET = SECRET;
        this.redisTemplate = redisTemplate;
    }
    public static long ACCESS_EXPIRATION_TIME = 120 * 60 * 1000L; // 2시간
    public static long REFRESH_EXPIRATION_TIME = 14 * 60 * 60 * 24 * 1000L; // 14일

    public LoginToken createLoginToken(User user) {
        String refreshToken = refreshToken();
        redisTemplate.opsForValue().set(user.getAccount(), refreshToken, 14, TimeUnit.DAYS);
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

    protected String refreshToken() {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET));
}

}
