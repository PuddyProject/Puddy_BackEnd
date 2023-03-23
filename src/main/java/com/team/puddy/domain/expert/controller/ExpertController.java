package com.team.puddy.domain.expert.controller;


import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.dto.ExpertFormDto;
import com.team.puddy.domain.expert.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;

    @PostMapping(value = "/profile/experts")
    public String newExpert(@Valid ExpertFormDto expertFormDto,
                            HttpServletRequest request,
                            BindingResult bindingResult){

        String referer = request.getHeader("Referer");

        if(bindingResult.hasErrors()){
            return "redirect:"+ referer;
        }

        try{
            Expert expert = Expert.createExpert(expertFormDto);
            expertService.saveExpert(expert);
        }catch (IllegalStateException e){
            return "redirect:"+ referer;
        }

        return "redirect:/";
    }
}
