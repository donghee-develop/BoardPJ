package com.test.board.domain.user.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.test.board.domain.user.entity.User;

@Getter
@AllArgsConstructor
public class ProfileResponseDto {
    private String email;
    private String name;
    private String profileImage;
    private String phoneNumber;
    private LocalDateTime createdAt;

    public static ProfileResponseDto from(User user) {
        return new ProfileResponseDto(
                user.getEmail(),
                user.getName(),
                user.getProfileImage(),
                user.getPhoneNumber(),
                user.getCreatedAt());
    }
}
