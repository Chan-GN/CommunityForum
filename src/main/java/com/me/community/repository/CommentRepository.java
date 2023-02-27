package com.me.community.repository;

import com.me.community.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value =
            "SELECT * FROM comment WHERE article_id = :articleId", nativeQuery = true)
    List<Comment> findAllByArticleId(@Param("articleId") Long articleId);
}
