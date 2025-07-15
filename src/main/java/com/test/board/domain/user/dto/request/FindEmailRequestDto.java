package com.test.board.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindEmailRequestDto {
    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    @Pattern(
            regexp = "^01[016789]-?\\d{3,4}-?\\d{4}$",
            message = "유효한 전화번호 형식이 아닙니다. (예: 010-1234-5678 또는 01012345678)")
    private String phoneNumber;
}
