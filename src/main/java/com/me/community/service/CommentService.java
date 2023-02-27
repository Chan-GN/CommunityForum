package com.me.community.service;

import com.me.community.dto.CommentDto;
import com.me.community.entity.Article;
import com.me.community.entity.Comment;
import com.me.community.entity.User;
import com.me.community.repository.CommentRepository;
import com.me.community.repository.UserRepository;
import com.me.community.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentDto create(Long articleId, Long userId, CommentDto dto) {
        // 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패 / 대상 게시글이 없음"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패, 해당하는 유저가 없음"));

        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(user, dto, article);
        // 댓글 엔티티를 DB로 저장
        Comment created = commentRepository.save(comment);
        // DTO 변경하여 반환
        return CommentDto.createCommentDto(created);
    }

    public List<CommentDto> findComments(Long articleId) {
        List<Comment> comments = commentRepository.findAllByArticleId(articleId);

        return comments.stream().map(c -> CommentDto.createCommentDto(c)).collect(Collectors.toList());
    }
}
