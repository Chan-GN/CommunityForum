package com.me.community.repository;

import com.me.community.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByArticleIdAndUserId(Long articleId, Long userId);

    Optional<List<Bookmark>> findAllByUserId(Long userId);

    List<Bookmark> findAllByArticleId(Long articleId);
}
