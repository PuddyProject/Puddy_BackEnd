package com.team.puddy.global.email;

import com.team.puddy.domain.user.dto.request.FindPasswordDto;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/users/find-password")
    public Response<String> findPassword(@RequestBody FindPasswordDto passwordDto) {
        emailService.findPassword(passwordDto.account(),passwordDto.username(),passwordDto.email());
        return Response.success("");
    }

    @PostMapping("/experts/send-docs")
    public Response<Void> sendDocs(@RequestPart("file") MultipartFile file,
                                   @AuthenticationPrincipal JwtUserDetails user) throws IOException {
        emailService.sendDocs(file,user.getUserId());
        return Response.success();
    }
}
