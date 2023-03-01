package com.me.community.controller;

import com.me.community.dto.ArticleResponseDto;
import com.me.community.service.BookmarkService;
import com.me.community.dto.ArticleDto;
import com.me.community.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor // 생성자 주입 (final 키워드)
public class ArticleApiController {

    private final ArticleService articleService;
    private final BookmarkService bookmarkService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    /**
     * 게시글 조회
     * 작성 시간으로 정렬
     */
    @GetMapping("/api/articles")
    public ResponseEntity<Result<List<ArticleResponseDto>>> list() {
        List<ArticleResponseDto> dtoList = articleService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(dtoList));
    }

    /**
     * 특정 게시글 조회
     */
    @GetMapping("/api/articles/{article-id}")
    public ResponseEntity<Result<ArticleResponseDto>> detail(
            @PathVariable("article-id") Long articleId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        checkDuplicateHits(articleId, request, response);

        ArticleResponseDto articleDto = articleService.findArticleById(articleId);

        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(articleDto));
    }

    /**
     * 게시글 저장
     */
    @PostMapping("/api/articles")
    public ResponseEntity<Result<ArticleDto>> detail(@RequestBody ArticleDto dto, @AuthenticationPrincipal User user) {
        ArticleDto articleDto = articleService.create(Long.valueOf(user.getUsername()), dto);

        // Wrapper 클래스로 감싼 후,
        // ResponseEntity 의 body 에 담아 반환
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(articleDto));
    }


    /**
     * 게시글 수정
     */
    @PutMapping("/api/articles/{article-id}")
    public ResponseEntity<Result<ArticleDto>> update(@PathVariable("article-id") Long articleId, @RequestBody ArticleDto dto) {
        ArticleDto articleDto  = articleService.update(articleId, dto);

        // Wrapper 클래스로 감싼 후,
        // ResponseEntity 의 body 에 담아 반환
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(articleDto));
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/api/articles/{article-id}")
    public ResponseEntity<Result<ArticleDto>> delete(@PathVariable("article-id") Long articleId) {
        ArticleDto articleDto = articleService.delete(articleId);

        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(articleDto));
    }

    /**
     * 게시글 북마크
     */
    @PostMapping("/api/articles/{article-id}/bookmark")
    public Long bookmark(
            @PathVariable("article-id") Long articleId,
            @AuthenticationPrincipal User user
    ) {

        return bookmarkService.bookmark(articleId, Long.valueOf(user.getUsername()));
    }

    /**
     * 게시글 북마크 취소
     */
    @DeleteMapping("/api/articles/{article-id}/bookmark")
    public Long unBookmark(
            @PathVariable("article-id") Long articleId,
            @AuthenticationPrincipal User user
    ) {

        return bookmarkService.unBookmark(articleId, Long.valueOf(user.getUsername()));
    }

    /**
     * 북마크한 게시글 조회
     */
    @GetMapping("/api/bookmark")
    public ResponseEntity<List<ArticleResponseDto>> bookmarks(@AuthenticationPrincipal User user) {
        List<ArticleResponseDto> bookmarkArticles = bookmarkService.findBookmarkArticle(Long.valueOf(user.getUsername()));

        return ResponseEntity.status(HttpStatus.OK).body(bookmarkArticles);
    }

    private void checkDuplicateHits(Long articleId, HttpServletRequest request, HttpServletResponse response) {

        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals("articleHit"))
                    oldCookie = cookie;

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + articleId.toString() + "]")) {
                articleService.updateHits(articleId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + articleId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            articleService.updateHits(articleId);
            Cookie newCookie = new Cookie("articleHit", "[" + articleId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }

    @GetMapping("/api/articles/{article-id}/bookmark")
    public boolean checkBookmark(
            @PathVariable("article-id") Long articleId,
            @AuthenticationPrincipal User user
    ) {

        return bookmarkService.check(articleId, Long.valueOf(user.getUsername()));
    }

    // 확장성을 위한 Wrapper 클래스
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
