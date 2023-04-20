package com.team.puddy.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.team.puddy.domain.type.JwtProvider;
import com.team.puddy.domain.user.dto.request.OauthUserRequest;
import com.team.puddy.domain.user.service.OauthService;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.KakaoOauth2Utils;
import com.team.puddy.global.config.auth.KakaoUserDetails;
import com.team.puddy.global.config.security.jwt.JwtTokenUtils;
import com.team.puddy.global.config.security.jwt.LoginToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OauthController {

    private final OauthService oauthService;


    @PostMapping("/kakao")
    public Response<LoginToken> kakaoLogin(@RequestHeader("Authorization") String accessToken) throws JsonProcessingException {

        KakaoUserDetails userInfo = KakaoOauth2Utils.getKakaoUserInfo(accessToken);
        LoginToken loginToken = oauthService.loginOauthUser(userInfo);

        return Response.success(loginToken);
    }

    @PostMapping("/join")
    public Response<LoginToken> registerOauth(@RequestHeader("Authorization") String accessToken,
                                       @RequestBody OauthUserRequest userRequest) throws JsonProcessingException {

        LoginToken loginToken = oauthService.JoinOauthUser(accessToken, userRequest);
        return Response.success(loginToken);
    }
}
