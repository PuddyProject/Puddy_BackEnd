package com.team.puddy.domain.answer.dto;

import lombok.Builder;

public record ResponseAnswerDto(Long id,
                                String content,
                                String nickname,
                                int postCategory,
                                String userRole,
                                boolean selected
                                ) {

    @Builder
    public ResponseAnswerDto {
    }
}
