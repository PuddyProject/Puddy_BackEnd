package com.team.puddy.domain.comment.service;

import com.team.puddy.domain.answer.domain.Answer;
import com.team.puddy.domain.answer.dto.RequestAnswerDto;
import com.team.puddy.domain.article.domain.Article;
import com.team.puddy.domain.article.repository.ArticleQueryRepository;
import com.team.puddy.domain.article.repository.ArticleRepository;
import com.team.puddy.domain.comment.domain.Comment;
import com.team.puddy.domain.comment.dto.request.RequestCommentDto;
import com.team.puddy.domain.comment.repository.CommentRepository;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserQueryRepository;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final UserQueryRepository userQueryRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    @Transactional
    public void addComment(RequestCommentDto request, Long articleId, Long userId) {
        User findUser = userQueryRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        Article findArticle = articleRepository.findById(articleId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Comment comment = commentMapper.toEntity(request, findArticle, findUser);
        commentRepository.save(comment);
    }

}
