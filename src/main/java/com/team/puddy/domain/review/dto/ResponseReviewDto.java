package com.team.puddy.domain.review.dto;

import lombok.Builder;

public record ResponseReviewDto(String content) {

    @Builder
    public ResponseReviewDto {
    }
}
