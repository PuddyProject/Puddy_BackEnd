package com.team.puddy.domain.user.dto.response;

data class TokenReissueResponse(
        val refreshToken : String,
        val accessToken : String
)
