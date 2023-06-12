package com.team.puddy.domain.expert.dto;

import lombok.Builder;

import java.util.List;

public record ResponseExpertListDto(
        List<ResponseExpertDto> expertList,
        boolean hasNextPage) {

    @Builder
    public ResponseExpertListDto {
    }
}
