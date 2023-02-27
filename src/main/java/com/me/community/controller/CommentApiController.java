package com.me.community.controller;

import com.me.community.dto.CommentDto;
import com.me.community.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;

    // 게시글의 댓글
    @GetMapping("/api/articles/{article-id}/comments")
    public ResponseEntity<Result<List<CommentDto>>> list(@PathVariable("article-id") Long articleId) {
        List<CommentDto> dtos = commentService.findComments(articleId);

        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(dtos));
    }


    @PostMapping("/api/articles/{article-id}/comments")
    public ResponseEntity<Result<CommentDto>> create(
            @PathVariable("article-id") Long articleId
            , @RequestBody CommentDto dto
            , @AuthenticationPrincipal User user
    ) {

        CommentDto created = commentService.create(articleId, Long.valueOf(user.getUsername()), dto);

        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(created));
    }



    // 확장성을 위한 Wrapper 클래스
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
