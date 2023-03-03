package com.me.community.service;

import com.me.community.entity.Bookmark;
import com.me.community.entity.Member;
import com.me.community.repository.BookmarkRepository;
import com.me.community.dto.ArticleResponseDto;
import com.me.community.entity.Article;
import com.me.community.repository.ArticleRepository;
import com.me.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;


    /**
     * 게시글 북마크
     */
    @Transactional
    public Long bookmark(Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("북마크 실패, 해당하는 게시글이 없음"));
        
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("북마크 실패, 해당하는 유저가 없음"));

        Bookmark bookmark = Bookmark.createBookmark(article, member);

        Bookmark saved = bookmarkRepository.save(bookmark);
        return saved.getId();
    }

    /**
     * 게시글 북마크 취소
     */
    @Transactional
    public Long unBookmark(Long articleId, Long userId) {
        Bookmark target = bookmarkRepository.findByArticleIdAndMemberId(articleId, userId)
                .orElseThrow(() -> new IllegalArgumentException("북마크 실패, 해당하는 게시글이 없음"));

        bookmarkRepository.delete(target);

        return target.getId();
    }

    public boolean check(Long articleId, Long userId) {
        Optional<Bookmark> bookmark = bookmarkRepository.findByArticleIdAndMemberId(articleId, userId);

        return bookmark.isPresent();
    }

    /**
     * 북마크한 게시글
     */
    public List<ArticleResponseDto> findBookmarkArticle(Long userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findAllByMemberId(userId)
                .orElseThrow(() -> new IllegalArgumentException("북마크 게시글 조회 실패, 북마크한 게시글이 없습니다."));

        List<Article> articles = bookmarks.stream()
                .map(bookmark -> articleRepository.findById(bookmark.getArticle().getId())
                        .orElseThrow(() -> new IllegalArgumentException("북마크 게시글 조회 실패, 해당하는 게시글이 없습니다.")))
                .collect(Collectors.toList());

        return articles.stream().map(ArticleResponseDto::new).collect(Collectors.toList());

    }

}
