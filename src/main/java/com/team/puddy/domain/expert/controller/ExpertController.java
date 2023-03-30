package com.team.puddy.domain.expert.controller;


import com.amazonaws.services.ec2.model.SpotInstanceType;
import com.team.puddy.domain.answer.dto.RequestAnswerDto;
import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.dto.ExpertDto;
import com.team.puddy.domain.expert.dto.ExpertFormDto;
import com.team.puddy.domain.expert.repository.ExpertRepository;
import com.team.puddy.domain.expert.service.ExpertService;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import com.team.puddy.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;

    private final ExpertRepository expertRepository;

    @PostMapping(value = "/profile/experts")
    public Response<?> newExpert(@RequestBody @Valid ExpertFormDto expertFormDto,
                                 @AuthenticationPrincipal JwtUserDetails user,
                                 BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return Response.error(ErrorCode.INVALID_ERROR.getMessage());
        }

        try{
            Expert expert = Expert.createExpert(expertFormDto, user.getUserId());
            expertService.saveExpert(expert,  user.getUserId());
        }catch (IllegalStateException e){
            return Response.error(ErrorCode.DUPLICATE_EXPERT_ACCOUNT.getMessage());
        }

        return Response.success();
    }

    @GetMapping(value = "/experts/{userId}")
    public Response<ExpertDto> viewExpert(@PathVariable("userId") Long userId) {

        return Response.success(expertService.loadExpertByUserId(userId));
    }
}
