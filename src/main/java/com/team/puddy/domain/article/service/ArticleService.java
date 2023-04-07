package com.team.puddy.domain.article.service;

import com.team.puddy.domain.question.dto.request.RequestQuestionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {
    public void addArticle(RequestQuestionDto requestDto, List<MultipartFile> images, Long userId) {

    }
}
