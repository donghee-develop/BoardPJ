package com.test.board.domain.board.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.test.board.config.BaseEntity;

@Entity
@Table(name = "boards")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Boolean isActive = true;
}
