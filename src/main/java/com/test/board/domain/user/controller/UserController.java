package com.test.board.domain.user.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.test.board.domain.auth.AuthUser;
import com.test.board.domain.auth.UserPrincipal;
import com.test.board.domain.user.dto.request.FindEmailRequestDto;
import com.test.board.domain.user.dto.request.FindPasswordRequestDto;
import com.test.board.domain.user.dto.request.SignUpRequestDto;
import com.test.board.domain.user.dto.response.FindEmailResponseDto;
import com.test.board.domain.user.dto.response.ProfileResponseDto;
import com.test.board.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponseDto> getMe(@AuthUser UserPrincipal userPrincipal) {
        ProfileResponseDto dto = userService.getMe(userPrincipal.getId());
        return ResponseEntity.ok(dto);
    }

    /** TODO : 휴대폰 인증 고려 */
    @PostMapping("/find-email")
    public ResponseEntity<FindEmailResponseDto> findEmail(
            @Valid @RequestBody FindEmailRequestDto findEmailRequestDto) {
        FindEmailResponseDto dto =
                userService.findEmailByPhone(findEmailRequestDto.getPhoneNumber());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/find-password")
    public ResponseEntity<Void> findPassword(
            @Valid @RequestBody FindPasswordRequestDto findPasswordRequestDto) {
        userService.sendTemporaryPassword(findPasswordRequestDto.getEmail());
        return ResponseEntity.ok().build();
    }
}
