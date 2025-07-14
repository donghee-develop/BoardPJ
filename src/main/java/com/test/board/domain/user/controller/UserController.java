package com.test.board.domain.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.test.board.config.auth.UserPrincipal;
import com.test.board.domain.user.dto.request.LoginRequestDto;
import com.test.board.domain.user.dto.request.SignUpRequestDto;
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
        ProfileResponseDto res = userService.getMe(userPrincipal.getId());
        return ResponseEntity.ok(res);
    }
}
