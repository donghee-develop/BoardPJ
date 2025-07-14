package com.test.board.domain.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.test.board.config.BaseEntity;

@Entity
@Table(name = "boards")
public class Board extends BaseEntity {
    private String title;
    private String content;
}
