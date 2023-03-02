package com.me.community.entity;

import com.me.community.dto.ArticleDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Table(name = "article")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(fetch = FetchType.LAZY) // JPA 활용 시, XToOne 인 경우 fetch 타입을 LAZY 로 설정 !!!
    @JoinColumn(name = "user_id")
    private User user;

    private Article(String title, Content content, int hits, int bookmarkHits, User user) {
        this.title = title;
        this.content = content;
        this.hits = hits;
        this.bookmarkHits = bookmarkHits;
        this.user = user;
    }

    // 생성 메소드
    public static Article createArticle(User user, ArticleDto dto) {
        return new Article(
                dto.getTitle(),
                dto.getContent(),
                0,
                0,
                user
        );
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
