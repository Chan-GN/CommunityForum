package com.hansung.hansungcommunity.service;

import com.me.community.dto.ArticleDto;
import com.me.community.entity.Article;
import com.me.community.entity.User;
import com.me.community.repository.ArticleRepository;
import com.me.community.repository.BookmarkRepository;
import com.me.community.repository.UserRepository;
import com.me.community.service.ArticleService;
import com.me.community.service.UserService;
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
    UserRepository userRepository;
    @Mock
    ArticleRepository articleRepository;

    @Mock
    BookmarkRepository bookmarkRepository;

    @Test
    public void post() {
        // given
        User user = new User();
        user.setName("test");
        user.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        UserService userService = new UserService(userRepository);
        Long uid = userService.join(user);

        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(1L);
        articleDto.setTitle("제목");

        Article article = Article.createArticle(user, articleDto);
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        ArticleService articleService = new ArticleService(articleRepository, userRepository, bookmarkRepository);

        // when
        ArticleDto post = articleService.post(uid, articleDto);

        // then
        assertEquals("제목", post.getTitle());
    }


}