package com.team.puddy.global.config;

import com.team.puddy.global.config.auth.JwtUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        if (authentication != null && authentication.getPrincipal() instanceof JwtUserDetails) {
            userId = String.valueOf(((JwtUserDetails) authentication.getPrincipal()).getUserId());
        }
        return Optional.of(userId);
    }
}