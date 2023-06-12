package com.team.puddy.domain.article.dto.request;

import java.util.List;

public record ServiceArticleDto(String title,
                                String content,
                                List<String> tagList,
                                int postCategory) {
}
