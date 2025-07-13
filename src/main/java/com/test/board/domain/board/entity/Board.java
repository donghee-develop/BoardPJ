package com.test.board.domain.board.entity;

import com.test.board.config.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "boards")
public class Board extends BaseEntity {
    private String title;
    private String content;
}
