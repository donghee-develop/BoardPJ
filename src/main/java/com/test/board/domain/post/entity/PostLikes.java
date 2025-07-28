package com.test.board.domain.post.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.test.board.config.BaseEntity;
import com.test.board.domain.user.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(
        name = "post_likes",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"post_id", "user_id"})})
public class PostLikes extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private LocalDateTime likedAt = LocalDateTime.now();

    public static PostLikes of(Post post, Long userId) {
        User user = User.builder().id(userId).build();
        return new PostLikes(post, user, LocalDateTime.now());
    }
}
