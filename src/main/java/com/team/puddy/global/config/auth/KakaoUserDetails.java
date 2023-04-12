package com.team.puddy.global.config.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserDetails implements OauthUserInfo {
    private final String sub;
    private final String name;
    private final String nickname;
    private final String picture;
    private final String email;


    @Override
    public String sub() {
        return sub;
    }

    @Override
    public String email() {
        return email;
    }
}