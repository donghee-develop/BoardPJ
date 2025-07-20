package com.test.board.domain.post.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.test.board.config.SoftDeletableEntity;
import com.test.board.domain.board.entity.Board;
import com.test.board.domain.user.entity.User;

@Entity
@Table(name = "posts")
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
}
