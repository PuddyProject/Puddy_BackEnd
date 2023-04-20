package com.team.puddy.global.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.team.puddy.domain.TestEntityUtils;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.global.config.auth.AuthConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@DisplayName("토큰 발행 테스트")
@ExtendWith(MockitoExtension.class)
public class JwtTokenUtilsTest {

    @InjectMocks
    private JwtTokenUtils jwtTokenUtils;

    private RedisTemplate<String,String> redisTemplate;


    private String SECRET = "104f205379c2ff5cdfa3a61a1b5a860193072bbeebf6f90356595a81d6b752a2";

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = TestEntityUtils.user();
        jwtTokenUtils = new JwtTokenUtils(SECRET,redisTemplate);

    }

//    @Test
//    void 토큰_발행_성공() {
//        when(refreshTokenRepository.findByAccount(testUser.getAccount()))
//                .thenReturn(Optional.empty());
//
//        LoginToken loginToken = jwtTokenUtils.createLoginToken(testUser);
//
//        assertNotNull(loginToken);
//        assertNotNull(loginToken.accessToken());
//        assertNotNull(loginToken.refreshToken());
//
//        DecodedJWT accessToken = JWT.decode(loginToken.accessToken().substring(AuthConstants.TOKEN_PREFIX.getValue().length()));
//        DecodedJWT refreshToken = JWT.decode(loginToken.refreshToken().substring(AuthConstants.TOKEN_PREFIX.getValue().length()));
//
//        assertEquals(testUser.getAccount(), accessToken.getSubject());
//        assertEquals(testUser.getId(), accessToken.getClaim("id").asLong());
//        assertEquals(testUser.getUsername(), accessToken.getClaim("username").asString());
//        assertEquals(testUser.getNickname(), accessToken.getClaim("nickname").asString());
//        assertEquals(testUser.getEmail(), accessToken.getClaim("email").asString());
//        assertEquals(testUser.getRole(), accessToken.getClaim("auth").asString());
//
//        verify(refreshTokenRepository, times(1)).findByAccount(eq(testUser.getAccount()));
//        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
//    }
}