package com.test.board.domain.user.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import org.hibernate.annotations.SQLRestriction;

import lombok.*;
import lombok.experimental.SuperBuilder;

import com.test.board.config.SoftDeletableEntity;
import com.test.board.domain.user.dto.request.SignUpRequestDto;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@SQLRestriction("deleted = false")
public class User extends SoftDeletableEntity {

    @Column(nullable = false)
    private String email;

    private String password;
    private String name;
    private String phoneNumber;

    // orphanRemoval 중간 테이블 값 삭제
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserBoardRole> userRoles = new HashSet<>();

    private String profileImage;

    public static User from(SignUpRequestDto dto, String encodedPassword) {
        return new User(
                dto.getEmail(),
                encodedPassword,
                dto.getName(),
                dto.getPhoneNumber(),
                new HashSet<>(),
                null);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void addUserBoardRole(UserBoardRole role) {
        if (userRoles == null) userRoles = new HashSet<>();
        userRoles.add(role);
    }
}
