package com.team.puddy.domain.expert.dto;


import com.team.puddy.domain.image.domain.Image;
import lombok.Builder;

import java.util.List;

public record ResponseExpertDto(
                         String username,
                         String introduce,
                         List<String> careerList,
                         Image image,
                         String location,
                         String education){


    @Builder
    public ResponseExpertDto {

    }
}
