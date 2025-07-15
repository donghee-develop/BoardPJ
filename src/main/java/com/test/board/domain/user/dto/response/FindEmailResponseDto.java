package com.test.board.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindEmailResponseDto {
    private String email;

    public static FindEmailResponseDto from(String email) {
        return new FindEmailResponseDto(email);
    }
}
