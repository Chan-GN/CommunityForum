package com.me.community.entity;

import com.me.community.dto.ArticleDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Table(name = "article")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends AuditingEntity {
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

    @ManyToOne(fetch = FetchType.LAZY) // JPA 활용 시, XToOne 인 경우 fetch 타입을 LAZY 로 설정 !!!
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "article", orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    private Article(String title, Content content, int hits, Member member) {
        this.title = title;
        this.content = content;
        this.hits = hits;
        this.member = member;
    }

    // 생성 메소드
    public static Article createArticle(Member member, ArticleDto dto) {
        return new Article(
                dto.getTitle(),
                dto.getContent(),
                0,
                member
        );
    }

    // 연관관계 메소드
    public void setMember(Member member) {
        this.member = member;
        member.getPostArticles().add(this); // 필요한가?
    }

    // 비즈니스 메소드
    public void patch(ArticleDto dto) {
        if (dto.getTitle() != null)
            this.title = dto.getTitle();

        if (dto.getContent() != null)
            this.content = dto.getContent();

        modified();
    }

    public void increaseHits() {
        this.hits = this.hits + 1;
    }
}
