package com.team.puddy.domain;

import com.team.puddy.domain.type.UserRole;
import com.team.puddy.domain.user.domain.User;
import org.springframework.test.util.ReflectionTestUtils;

public class TestEntityUtils {

    public static User user(String account, String password) {
        return User.builder()
                .id(1L)
                .username("상준")
                .account(account)
                .password(password)
                .nickname("퍼디1234")
                .email(account + "@email.com")
                .role(UserRole.USER.getRole())
                .build();
    }
}
