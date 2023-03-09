package com.me.community.repository;

import com.me.community.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByArticleIdAndMemberId(Long articleId, Long memberId);

}
