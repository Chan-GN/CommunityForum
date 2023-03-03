package com.me.community.dto;

import com.me.community.entity.Article;
import com.me.community.entity.Content;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private String title;
    private Content content;
    private LocalDateTime postDate; // 작성 시간
    private int hits; // 조회수
    private int bookmarkHits; // 북마크 횟수

    private String username;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.hits = article.getHits();
        this.bookmarkHits = article.getBookmarkHits();
        this.username = article.getMember().getName();
        this.postDate = article.getCreatedAt();
    }

}
