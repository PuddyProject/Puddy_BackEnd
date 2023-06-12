package com.team.puddy.domain.expert.controller;

import com.team.puddy.domain.expert.dto.RequestExpertDto;
import com.team.puddy.domain.expert.dto.ResponseExpertDto;
import com.team.puddy.domain.expert.dto.ResponseExpertListDto;
import com.team.puddy.domain.expert.dto.UpdateExpertDto;
import com.team.puddy.domain.expert.service.ExpertService;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ExpertController {

    private final ExpertService expertService;

    @ResponseStatus(HttpStatus.CREATED)
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

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/experts")
    public Response<Void> updateExpert(@AuthenticationPrincipal JwtUserDetails user,
                                       @RequestBody UpdateExpertDto request) {
        expertService.updateExpert(user.getUserId(), request);
        return Response.success();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/experts")
    public Response<ResponseExpertListDto> getExpertList(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        ResponseExpertListDto experts = expertService.getExpertList(pageable);
        return Response.success(experts);
    }



    


}
