package com.team.puddy.global.config.security.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByAccount(String account);

    Optional<RefreshToken> findByToken(String token);

    void deleteByAccount(String account);



    void delete(RefreshToken refreshToken);
}
