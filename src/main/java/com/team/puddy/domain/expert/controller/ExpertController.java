package com.team.puddy.domain.expert.controller;

import com.team.puddy.domain.expert.dto.RequestExpertDto;
import com.team.puddy.domain.expert.dto.ResponseExpertDto;
import com.team.puddy.domain.expert.service.ExpertService;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/users/experts")
    public Response<?> registerExpert(@RequestBody @Valid RequestExpertDto requestDto,
                                      @AuthenticationPrincipal JwtUserDetails user
    ) {
        expertService.registerExpert(requestDto, user.getUserId());


        return Response.success();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/experts/{userId}")
    public Response<ResponseExpertDto> getExpert(@PathVariable("userId") Long userId) {
        ResponseExpertDto expertDto = expertService.getExpertByUserId(userId);
        return Response.success(expertDto);
    }
}
