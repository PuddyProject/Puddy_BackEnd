package com.team.puddy.domain.answer.dto;

import lombok.Builder;

public record ResponseAnswerDtoExcludeUser(Long id,
                                           String content,
                                           String nickname,
                                           int postCategory,
                                           String userRole,
                                           boolean selected
) {
    @Builder
    public ResponseAnswerDtoExcludeUser {
    }
}
