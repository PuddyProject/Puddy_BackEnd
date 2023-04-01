package com.team.puddy.domain.expert.dto;


import lombok.Builder;

import java.util.List;

public record ResponseExpertDto(
                         String username,
                         String introduce,
                         List<String> careerList,
                         String location,
                         String education){


    @Builder
    public ResponseExpertDto {

    }
}
