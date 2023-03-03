package com.me.community.dto;

import com.me.community.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
    private Long id;
    private Long articleId;
    private String body;

    private String username;

    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getArticle().getId(), comment.getBody(), comment.getMember().getName());
    }
}
