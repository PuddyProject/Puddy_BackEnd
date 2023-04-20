package com.team.puddy.global.config.security;

import com.team.puddy.domain.type.UserRole;
import com.team.puddy.global.config.WithMockAuthUser;
import com.team.puddy.global.config.auth.JwtUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.test.util.ReflectionTestUtils;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockAuthUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockAuthUser annotation) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        JwtUserDetails authentication = new JwtUserDetails();
        ReflectionTestUtils.setField(authentication, "id", 1L);
        ReflectionTestUtils.setField(authentication, "role", UserRole.USER.name());
        ReflectionTestUtils.setField(authentication, "isAuthenticated", true);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authentication, null, authentication.getAuthorities());

        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}
