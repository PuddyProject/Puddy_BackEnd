package com.team.puddy.domain.user.dto.response;

data class UserInfoResponse(
    val nickname: String,
    val imagePath: String,
    val hasPet: Boolean,
    val hasExpertInfo: Boolean
)
