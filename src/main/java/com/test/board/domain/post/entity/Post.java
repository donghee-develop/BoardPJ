package com.test.board.domain.post.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.test.board.config.SoftDeletableEntity;
import com.test.board.domain.board.entity.Board;
import com.test.board.domain.user.entity.User;

@Entity
@Table(
        name = "posts",
        indexes = {
            @Index(name = "idx_posts_board_created", columnList = "board_id, created_at DESC"),
            @Index(name = "idx_posts_user_created", columnList = "user_id, created_at DESC"),
            @Index(name = "idx_posts_title", columnList = "title") // (선택적, LIKE 검색 많이 쓰면)
        })
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends SoftDeletableEntity {
    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    private Integer likeCount;

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) this.likeCount--;
    }
}
