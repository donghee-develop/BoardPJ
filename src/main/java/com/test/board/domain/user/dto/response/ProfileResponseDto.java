package com.test.board.domain.user.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileResponseDto {
    private String email;
    private String name;
    private String profileImage;
    private String phoneNumber;
    private LocalDateTime createdAt;
}
