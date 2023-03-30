package com.team.puddy.domain.expert.dto;


import lombok.Builder;

public record ExpertDto ( //private String profileImg;
                         String userName,
                         String introduce,
                         String career,
                         String location,
                         String education){


    @Builder
    public ExpertDto{

    }
}
