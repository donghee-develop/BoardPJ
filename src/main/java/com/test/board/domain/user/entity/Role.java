package com.test.board.domain.user.entity;

import jakarta.persistence.*;

import lombok.Getter;

import com.test.board.config.BaseEntity;

@Entity
@Table(name = "roles")
@Getter
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private Roles name;

    public void updateName(Roles roleEnum) {
        this.name = roleEnum;
    }

    public enum Roles {
        SUPER_ADMIN,
        SUB_ADMIN,
        BOARD_MANAGER,
        USER
    }
}
