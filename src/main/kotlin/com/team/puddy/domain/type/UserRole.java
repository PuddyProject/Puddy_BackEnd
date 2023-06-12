package com.team.puddy.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;



import org.springframework.security.core.GrantedAuthority;


import org.springframework.security.core.GrantedAuthority;


@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER("ROLE_USER"),
    EXPERT("ROLE_EXPERT");
    private final String role;
}
