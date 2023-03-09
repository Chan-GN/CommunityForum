package com.me.community.repository;

import com.me.community.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a from Article a" +
            " join fetch a.bookmarks b" +
            " join fetch a.member m" +
            " where b.member.id = :userId")
    List<Article> findAllWithBookmark(@Param("userId") Long userId);

}
