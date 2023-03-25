package com.team.puddy.domain.user.controller;

import com.team.puddy.domain.type.UserRole;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.dto.request.DuplicateAccountRequest;
import com.team.puddy.domain.user.dto.request.DuplicateEmailRequest;
import com.team.puddy.domain.user.dto.request.LoginUserRequest;
import com.team.puddy.domain.user.dto.request.RegisterUserRequest;
import com.team.puddy.domain.user.dto.response.TokenReissueDto;
import com.team.puddy.domain.user.service.UserService;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import com.team.puddy.global.config.security.jwt.LoginToken;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public Response<LoginToken> login(@RequestBody @Valid LoginUserRequest request) {
        LoginToken token = userService.login(request);
        return Response.success(token);
    }

    @PostMapping("/join")
    public Response<Void> join(@RequestBody @Valid RegisterUserRequest request) {
        userService.join(request);

        return Response.success();
    }

    @PostMapping("/login/reissue")
    public LoginToken tokenReissue(@RequestBody TokenReissueDto tokenReissueDto) {
        log.info("access: {}",tokenReissueDto.accessToken());
        log.info("refresh: {}",tokenReissueDto.refreshToken());
        return userService.reissueToken(tokenReissueDto);
    }

    @DeleteMapping(value = "/logout", produces = "application/json")
    public void logout(@AuthenticationPrincipal JwtUserDetails userDetails, HttpServletResponse response) {
        userService.logout(userDetails.getUserId());
    }



    @GetMapping("/duplicate-email")
    @Operation(summary = "이메일 중복 검사 메서드")
    public void duplicateEmail(@RequestBody DuplicateEmailRequest request) {

        userService.duplicateEmailCheck(request.email());
        log.error("중복된 이메일입니다.");
    }

    @GetMapping("/duplicate-account")
    @Operation(summary = "아이디 중복 검사 메서드")
    public void duplicateAccount(@RequestBody DuplicateAccountRequest request) {

        userService.duplicateAccountCheck(request.account());
    }

}
