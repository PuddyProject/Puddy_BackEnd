package com.team.puddy.domain.user.dto.request;

data class FindPasswordRequest(
    val account : String,
    val username : String,
    val email: String)
