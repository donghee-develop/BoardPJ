package com.test.board.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpUserDto {
    @NotNull(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    @NotNull(message = "이름은 필수 입력 값입니다.")
    @Size(min = 2, max = 30, message = "이름은 2자 이상 30자 이하로 입력하세요.")
    private String name;

    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 5, message = "비밀번호는 최소 5자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]|;:'\",.<>/?]).+$",
            message = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotNull(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^01([0|1|6|7|8|9])[-]?\\d{3,4}[-]?\\d{4}$",
            message = "올바른 전화번호 형식이어야 합니다. (예: 010-1234-5678)")
    private String phoneNumber;

}
