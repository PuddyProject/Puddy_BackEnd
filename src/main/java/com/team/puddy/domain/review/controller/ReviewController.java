package com.team.puddy.domain.review.controller;

import com.team.puddy.domain.review.dto.RequestReviewDto;
import com.team.puddy.domain.review.dto.ResponseReviewDto;
import com.team.puddy.domain.review.service.ReviewService;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{expertId}")
    public Response<Void> addReview(@PathVariable("expertId") Long expertId,
                                    @RequestBody RequestReviewDto requestDto,
                                    @AuthenticationPrincipal JwtUserDetails user) {
        reviewService.addReview(expertId, requestDto);

        return Response.success();
    }

    @GetMapping("/{expertId}")
    public Response<List<ResponseReviewDto>> getReviewList(@PathVariable("expertId") Long expertId) {
        List<ResponseReviewDto> reviewList = reviewService.getReviewListByExpertId(expertId);
        return Response.success(reviewList);
    }

}
