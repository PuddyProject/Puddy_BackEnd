package com.team.puddy.domain.expert.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team.puddy.domain.image.domain.Image;
import lombok.Builder;

import java.util.List;

public record ResponseExpertDto(Long expertId,
                         String username,
                         String introduce,
                         List<String> careerList,
                         @JsonIgnore
                         Image image,
                         String location,
                         String education){


    @Builder
    public ResponseExpertDto {

    }
}
