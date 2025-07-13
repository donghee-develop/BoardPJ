package com.test.board.domain.user.entity;

import com.test.board.config.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Column (nullable = false)
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String profileImage;
}

