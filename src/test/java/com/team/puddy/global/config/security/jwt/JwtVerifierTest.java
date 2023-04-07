package com.team.puddy.global.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.team.puddy.global.error.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@DisplayName("토큰 검증 테스트")
@ExtendWith(MockitoExtension.class)
public class JwtVerifierTest {

    @InjectMocks
    private JwtVerifier jwtVerifier;


    private final String testSecret = "testSecret";
    private final String testAccount = "testAccount";


    @BeforeEach
    public void setUp() {
        jwtVerifier.SECRET = testSecret;
    }

    @Test
    public void 유효한_토큰_검증() {
        // Given
        String validToken = createTestJwtToken();
        DecodedJWT decodedJWT = JWT.decode(validToken);

        // When
        DecodedJWT result = jwtVerifier.verify(validToken);

        // Then
        assertThat(result.getAlgorithm()).isEqualTo(decodedJWT.getAlgorithm());
        assertThat(result.getIssuer()).isEqualTo(decodedJWT.getIssuer());
        assertThat(result.getClaim("account").asString()).isEqualTo(decodedJWT.getClaim("account").asString());
    }

    @Test
    public void 유효한토큰_계정추출시_성공() {
        // Given
        String validToken = createTestJwtToken();
        DecodedJWT decodedJWT = JWT.decode(validToken);

        // When
        String account = jwtVerifier.parseAccount(validToken);

        // Then
        assertThat(account).isEqualTo(decodedJWT.getSubject());
    }

    @Test
    public void 비유효한토큰_계정추출시_예외() {
        // Given
        String invalidToken = "invalid.token";

        // When
        Throwable exception = catchThrowable(() -> jwtVerifier.parseAccount(invalidToken));

        // Then
        assertThat(exception).isInstanceOf(JWTDecodeException.class);
    }

    @Test
    public void 만료된토큰_검증시_예외() {
        // Given
        String expiredToken = createExpiredTestJwtToken();

        // When
        Throwable exception = catchThrowable(() -> jwtVerifier.verify(expiredToken));

        // Then
        assertThat(exception).isInstanceOf(BusinessException.class);
    }

    private String createTestJwtToken() {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 86400000L);

        return JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .withClaim("account", testAccount) // "account" 값을 설정
                .sign(Algorithm.HMAC256(testSecret));
    }

    private String createExpiredTestJwtToken() {
        Date now = new Date();
        Date expiry = new Date(now.getTime() - 86400000L);

        return JWT.create()
                .withSubject(testAccount)
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .withClaim("account",testAccount)
                .sign(Algorithm.HMAC256(testSecret));
    }
}