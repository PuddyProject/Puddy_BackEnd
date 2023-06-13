package com.team.puddy.domain.user.dto.request;

import com.team.puddy.global.annotation.oauth.JwtProvider;

data class OauthUserRequest(
    val isNotificated : Boolean,
    @JwtProvider val provider : String,
    )
