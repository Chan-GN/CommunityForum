package com.hansung.hansungcommunity.service;

import com.me.community.dto.ArticleDto;
import com.me.community.entity.Article;
import com.me.community.entity.Member;
import com.me.community.repository.ArticleRepository;
import com.me.community.repository.BookmarkRepository;
import com.me.community.repository.MemberRepository;
import com.me.community.service.ArticleService;
import com.me.community.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    ArticleRepository articleRepository;

    @Mock
    BookmarkRepository bookmarkRepository;

    @Test
    public void post() {
        // given
        Member member = new Member();
        member.setName("test");
        member.setId(1L);

        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));

        MemberService memberService = new MemberService(memberRepository);
        Long uid = memberService.join(member);

        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(1L);
        articleDto.setTitle("제목");

        Article article = Article.createArticle(member, articleDto);
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        ArticleService articleService = new ArticleService(articleRepository, memberRepository, bookmarkRepository);

        // when
        ArticleDto post = articleService.create(uid, articleDto);

        // then
        assertEquals("제목", post.getTitle());
    }


}