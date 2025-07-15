package com.test.board.domain.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.test.board.config.auth.UserPrincipal;
import com.test.board.domain.user.dto.request.FindEmailRequestDto;
import com.test.board.domain.user.dto.request.FindPasswordRequestDto;
import com.test.board.domain.user.dto.request.LoginRequestDto;
import com.test.board.domain.user.dto.request.SignUpRequestDto;
import com.test.board.domain.user.dto.response.FindEmailResponseDto;
import com.test.board.domain.user.dto.response.ProfileResponseDto;
import com.test.board.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        userService.login(loginRequestDto, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponseDto> getMe(HttpSession session) {
        UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute("user");
        ProfileResponseDto dto = userService.getMe(userPrincipal.getId());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
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
