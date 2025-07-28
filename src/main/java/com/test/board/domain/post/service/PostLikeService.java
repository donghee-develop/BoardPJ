package com.test.board.domain.post.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import com.test.board.config.error.CustomException;
import com.test.board.config.error.ErrorCode;
import com.test.board.domain.post.entity.Post;
import com.test.board.domain.post.entity.PostLikes;
import com.test.board.domain.post.repository.PostLikeRepository;
import com.test.board.domain.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostLikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void toggleLike(Long postId, Long userId) {
        Post post =
                postRepository
                        .findById(postId)
                        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Optional<PostLikes> existingLike = postLikeRepository.findByUserIdAndPostId(userId, postId);

        if (existingLike.isPresent()) {
            // 좋아요 취소
            postLikeRepository.delete(existingLike.get());
            post.decrementLikeCount();
            log.info("decrement -> {}", post.getLikeCount());

        } else {
            PostLikes newLike = PostLikes.of(post, userId);
            postLikeRepository.save(newLike);
            post.incrementLikeCount();
            log.info("increment -> {}", post.getLikeCount());
        }
    }
}
