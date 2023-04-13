package com.team.puddy.global.email;

import com.team.puddy.domain.user.dto.request.FindPasswordDto;
import com.team.puddy.global.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/find-password")
    public Response<String> findPassword(@RequestBody FindPasswordDto passwordDto) {
        emailService.findPassword(passwordDto.account(),passwordDto.username(),passwordDto.email());
        return Response.success("");
    }
}
