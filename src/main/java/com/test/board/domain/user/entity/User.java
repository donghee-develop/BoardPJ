package com.test.board.domain.user.entity;

import com.test.board.config.BaseEntity;
import com.test.board.domain.user.dto.request.SignUpUserDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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

    // orphanRemoval 중간 테이블 값 삭제
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserBoardRole> userRoles = new HashSet<>();


    private String profileImage;


    public static User from(SignUpUserDto dto, String encodedPassword) {
        return new User(
                dto.getEmail(),
                encodedPassword,
                dto.getName(),
                dto.getPhoneNumber(),
                new HashSet<>(),
                null
        );
    }

}

