package com.team.puddy.domain.user.dto.response;

import com.team.puddy.domain.article.dto.response.ResponseArticleExcludeCommentDto;
import com.team.puddy.domain.question.dto.response.ResponseQuestionExcludeAnswerDto;

data class MyPostResponse(
    val questionList: List<ResponseQuestionExcludeAnswerDto>,
    val articleList: List<ResponseArticleExcludeCommentDto>
)
