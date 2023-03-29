package com.team.puddy.domain.expert.controller;


import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.dto.ExpertFormDto;
import com.team.puddy.domain.expert.service.ExpertService;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import com.team.puddy.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;

    @PostMapping(value = "/profile/experts")
    public Response<?> newExpert(@RequestBody @Valid ExpertFormDto expertFormDto,
                                 @AuthenticationPrincipal JwtUserDetails user,
                                 BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return Response.error(ErrorCode.INVALID_ERROR.getMessage());
        }

        try{
            Expert expert = Expert.createExpert(expertFormDto, user.getUserId());
            expertService.saveExpert(expert);
        }catch (IllegalStateException e){
            return Response.error(ErrorCode.DUPLICATE_EXPERT_ACCOUNT.getMessage());
        }

        return Response.success();
    }
}
