package com.me.community.entity;

import com.me.community.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "comment")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends ModifiedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // JPA 활용 시, XToOne 인 경우 fetch 타입을 LAZY 로 설정 !!!
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;
    @Column
    private String body;

    public static Comment createComment(Member member, CommentDto dto, Article article) {
        return new Comment(
                dto.getId(),
                member,
                article,
                dto.getBody()
        );
    }

    public void setArticle(Article article) {
        this.article = article;
    }

}
