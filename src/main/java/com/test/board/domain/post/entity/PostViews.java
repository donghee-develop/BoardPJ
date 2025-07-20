package com.test.board.domain.post.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.test.board.config.BaseEntity;

@Table(name = "post_views")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostViews extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private Long viewCount = 0L;

    public void increment() {
        this.viewCount++;
    }
}
