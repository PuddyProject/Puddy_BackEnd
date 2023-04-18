package com.team.puddy.global.annotation.oauth;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class JwtProviderValidator implements ConstraintValidator<JwtProvider, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            com.team.puddy.domain.type.JwtProvider.valueOf(value);
        } catch (IllegalArgumentException ex) {
            return false;
        }

        return true;
    }
}