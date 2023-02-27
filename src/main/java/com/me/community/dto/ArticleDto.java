package com.me.community.dto;

import com.me.community.entity.Article;
import com.me.community.entity.Content;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    @NotNull
    private String title;
    private Content content;

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
