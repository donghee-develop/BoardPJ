package com.test.board.domain.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.board.domain.post.entity.PostLikes;

public interface PostLikeRepository extends JpaRepository<PostLikes, Long> {
    Optional<PostLikes> findByUserIdAndPostId(Long userId, Long postId);
}
