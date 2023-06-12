package com.team.puddy.domain.comment.dto.response;

import java.time.LocalDateTime;

public record ResponseCommentDto(Long id,
                                String content,
                                String nickname,
                                LocalDateTime createdDate) {
}
