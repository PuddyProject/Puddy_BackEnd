package com.team.puddy.domain.expert.controller;


import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.dto.ExpertFormDto;
import com.team.puddy.domain.expert.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/expert")
@RestController
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;

    @GetMapping(value = "/register")
    public String expertForm(){

        return "/profile/experts";
    }

    @PostMapping(value = "/register")
    public String newExpert(@Valid ExpertFormDto expertFormDto,
                            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "/profile/experts";
        }

        try{
            Expert expert = Expert.createExpert(expertFormDto);
            expertService.saveExpert(expert);
        }catch (IllegalStateException e){
            return "/profile/experts";
        }

        return "redirect:/";
    }
}
