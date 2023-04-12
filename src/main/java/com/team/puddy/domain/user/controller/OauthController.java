package com.team.puddy.domain.user.controller;

import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.KakaoOauth2Utils;
import com.team.puddy.global.config.auth.KakaoUserDetails;
import com.team.puddy.global.config.security.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {

    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/kakao")
    public Response<Void> kakaoLogin(@RequestHeader("Authorization") String accessToken) {

        KakaoUserDetails userInfo = KakaoOauth2Utils.getUserInfo(accessToken);

        return Response.success();
    }
}
