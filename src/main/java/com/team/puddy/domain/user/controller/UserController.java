package com.team.puddy.domain.user.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team.puddy.domain.question.service.QuestionService;
import com.team.puddy.domain.user.dto.response.ResponseUserDto;
import com.team.puddy.domain.user.dto.request.DuplicateAccountRequest;
import com.team.puddy.domain.user.dto.request.DuplicateEmailRequest;
import com.team.puddy.domain.user.dto.request.LoginUserRequest;
import com.team.puddy.domain.user.dto.request.RegisterUserRequest;
import com.team.puddy.domain.user.dto.response.TokenReissueDto;
import com.team.puddy.domain.user.service.UserService;
import com.team.puddy.global.common.S3UpdateUtil;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import com.team.puddy.global.config.security.jwt.LoginToken;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;
import com.team.puddy.global.error.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final S3UpdateUtil s3UpdateUtil;

    private final UserService userService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public Response<LoginToken> login(@RequestBody @Valid LoginUserRequest request) {
        LoginToken token = userService.login(request);
        return Response.success(token);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/join")
    public Response<Void> join(@RequestBody @Valid RegisterUserRequest request) {
        userService.join(request);
        return Response.success();
    }

    @PostMapping("/login/reissue")
    public LoginToken tokenReissue(@RequestBody TokenReissueDto tokenReissueDto) {
        log.info("access: {}", tokenReissueDto.accessToken());
        log.info("refresh: {}", tokenReissueDto.refreshToken());
        return userService.reissueToken(tokenReissueDto);
    }

    @DeleteMapping(value = "/logout", produces = "application/json")
    public void logout(@AuthenticationPrincipal JwtUserDetails userDetails, HttpServletResponse response) {
        userService.logout(userDetails.getUserId());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/me")
    public Response<?> me(@AuthenticationPrincipal JwtUserDetails user) {
        ResponseUserDto userInfo = userService.getUserInfo(user.getUserId());
        return Response.success(userInfo);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update-profile-image")
    public Response<?> updateProfileImage(@RequestParam(value = "file") MultipartFile file,
                                          @AuthenticationPrincipal JwtUserDetails user) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new NotFoundException(ErrorCode.IMAGE_NOT_FOUND);
        }

        String fileName = s3UpdateUtil.createFileName(file.getOriginalFilename());
        String imagePath = s3UpdateUtil.uploadToS3(file, fileName);

        userService.updateProfileImage(user.getUserId(), imagePath);
        return Response.success(imagePath);
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
