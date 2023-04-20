package com.team.puddy.domain.user.dto.request;

import com.team.puddy.global.annotation.oauth.JwtProvider;

public record OauthUserRequest(boolean isNotificated,

                               @JwtProvider String provider){

}

