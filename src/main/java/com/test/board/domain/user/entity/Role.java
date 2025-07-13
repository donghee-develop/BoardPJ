package com.test.board.domain.user.entity;

import com.test.board.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Column(unique = true, nullable = false)
    private Roles name;

    public enum Roles {
        SUPER_ADMIN,
        SUB_ADMIN,
        BOARD_MANAGER,
        USER
    }
}
