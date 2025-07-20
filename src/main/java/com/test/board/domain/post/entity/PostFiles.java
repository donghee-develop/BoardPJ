package com.test.board.domain.post.entity;

import com.test.board.config.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_files")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostFiles extends BaseEntity {
    private String originalName;
    private String savedName;
    private String path;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
