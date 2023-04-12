package com.team.puddy.global.config.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.puddy.global.error.exception.user.TokenInvalidException;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

public class KakaoOauth2Utils {

    private static final String USER_INFO_ENDPOINT = "https://kapi.kakao.com/v1/oidc/userinfo";

    public static KakaoUserDetails getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        // Set the access token in the Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Build the userinfo request
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    USER_INFO_ENDPOINT,
                    HttpMethod.GET,
                    request,
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.getBody(), KakaoUserDetails.class);
            }
        } catch (IOException | HttpClientErrorException e) {

        }

        throw new TokenInvalidException("토큰이 유효하지 않습니다");
    }
}