package com.team.puddy.domain.comment.controller;

import com.team.puddy.domain.comment.dto.request.RequestCommentDto;
import com.team.puddy.domain.comment.service.CommentService;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/articles/{articleId}/comments")
    public Response<Void> addComment (@PathVariable("articleId") Long articleId,
                                @RequestBody RequestCommentDto request,
                                @AuthenticationPrincipal JwtUserDetails user) {
            commentService.addComment(request,articleId,user.getUserId());
            return Response.success();
    }

}
