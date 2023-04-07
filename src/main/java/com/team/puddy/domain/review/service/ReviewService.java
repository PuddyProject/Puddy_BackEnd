package com.team.puddy.domain.review.service;

import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.repository.ExpertQueryRepository;
import com.team.puddy.domain.expert.repository.ExpertRepository;
import com.team.puddy.domain.expert.service.ExpertService;
import com.team.puddy.domain.review.domain.Review;
import com.team.puddy.domain.review.dto.RequestReviewDto;
import com.team.puddy.domain.review.dto.ResponseReviewDto;
import com.team.puddy.domain.review.repository.ReviewQueryRepository;
import com.team.puddy.domain.review.repository.ReviewRepository;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ExpertQueryRepository expertQueryRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    private final ReviewMapper reviewMapper;

    public void addReview(Long expertId, RequestReviewDto requestDto) {
        Expert findExpert = expertQueryRepository.findExpertById(expertId).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_THE_EXPERT));
        Review review = reviewMapper.toEntity(requestDto, findExpert);
        reviewRepository.save(review);
        findExpert.getReviewList().add(review);
    }

    public List<ResponseReviewDto> getReviewListByExpertId(Long expertId) {
        List<Review> reviews = reviewQueryRepository.findReviewListByExpertId(expertId);
        return reviews.stream().map(reviewMapper::toDto).toList();
    }
}
