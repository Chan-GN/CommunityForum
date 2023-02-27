package com.me.community.entity;

import com.me.community.dto.ArticleDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Table(name = "article")
@Getter
@Setter
@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;
    @NotNull
    private String title; // 제목
    @Embedded
    @AttributeOverride(name = "article", column = @Column(nullable = false))
    private Content content; // 내용
    private int hits; // 조회수
    private int bookmarkHits; // 북마크 횟수
    private int report; // 신고 횟수

    @ManyToOne(fetch = FetchType.LAZY) // JPA 활용 시, XToOne 인 경우 fetch 타입을 LAZY 로 설정 !!!
    @JoinColumn(name = "user_id")
    private User user;

    // private Image image; // 게시글 내부 이미지, 추후 개발

    // 생성 메소드
    public static Article createArticle(User user, ArticleDto dto) {
        Article article = new Article();

        article.setUser(user); // 연관관계 설정

        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setHits(0);
        article.setBookmarkHits(0);
        article.setReport(0);

        return article;
    }

    // 연관관계 메소드
    public void setUser(User user) {
        this.user = user;
        user.getPostArticles().add(this); // 필요한가?
    }

    // 비즈니스 메소드
    public void patch(ArticleDto dto) {
        if (dto.getTitle() != null)
            this.title = dto.getTitle();

        if (dto.getContent() != null)
            this.content = dto.getContent();
    }

    public void increaseHits() {
        this.hits = this.hits + 1;
    }
}
