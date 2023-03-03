package com.me.community.service;

import com.me.community.dto.ArticleDto;
import com.me.community.dto.ArticleResponseDto;
import com.me.community.entity.Article;
import com.me.community.entity.Bookmark;
import com.me.community.entity.Member;
import com.me.community.repository.ArticleRepository;
import com.me.community.repository.BookmarkRepository;
import com.me.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor // 생성자 주입 (final 키워드)
@Transactional(readOnly = true) // 읽기 전용
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;

    /**
     * 자유 게시글 게시
     */
    @Transactional // 필요 시 쓰기 전용
    public ArticleDto create(Long memberId, @Valid ArticleDto articleDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 게시 실패, 해당하는 유저가 없음"));

        Article article = Article.createArticle(member, articleDto); // 게시글 생성

        Article savedArticle = articleRepository.save(article); // DB에 저장

        return new ArticleDto(savedArticle); // DTO 변환 후 반환
    }

    /**
     * 자유 게시글 수정
     */
    @Transactional
    public ArticleDto update(Long articleId, ArticleDto dto) {
        Article target = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 수정 실패, 해당하는 게시글이 없음"));

        // 게시글 수정
        target.patch(dto);

        return dto;
    }

    /**
     * 자유 게시글 삭제
     */
    @Transactional
    public ArticleDto delete(Long articleId) {
        Article target = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 삭제 실패, 해당하는 게시글이 없음"));

        // 게시글 삭제
        articleRepository.delete(target);

        return new ArticleDto(target);
    }

    /**
     * 게시글 리스트 조회
     * 정렬 후 반환
     */
    public List<ArticleResponseDto> findAll() {
        List<Article> list = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        return list.stream()
                .map(ArticleResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글 조회
     */
    public ArticleResponseDto findArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 조회 실패, 해당하는 게시글이 없음"));

        List<Bookmark> bookmarks = bookmarkRepository.findAllByArticleId(articleId);
        ArticleResponseDto resDto = new ArticleResponseDto(article);
        resDto.setBookmarkHits(bookmarks.size());

        return resDto;
    }

    /**
     * 조회수 증가
     */
    @Transactional
    public void updateHits(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 조회수 증가 실패, 해당하는 게시글이 없음"));

        article.increaseHits(); // 변경 감지 활용, 조회수 증가
    }

}

