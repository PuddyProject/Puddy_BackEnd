package com.team.puddy.domain.comment.dto.response;

import java.time.LocalDateTime;

public record ResponseCommentDto(String content,
                                String nickname,
                                LocalDateTime createdDate) {
}
