package com.team.puddy.global.config.security.jwt;

import com.team.puddy.domain.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "RefreshToken")
public class RefreshToken extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private String account;

    public RefreshToken(String token, String account) {
        this.token = token;
        this.account = account;
    }
}