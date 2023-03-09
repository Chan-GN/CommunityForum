package com.me.community.dto;

import com.me.community.entity.Article;
import com.me.community.entity.Bookmark;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ArticleListResponseDto {
    private Long id;
    private String title;
    private LocalDateTime postDate; // 작성 시간
    private String username;

    private Bookmark bookmarks;

    public ArticleListResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.username = article.getMember().getName();
        this.postDate = article.getCreatedAt();
    }

}
