package com.test.board.domain.admin.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.test.board.config.auth.dto.request.LoginRequestDto;
import com.test.board.config.auth.service.AuthService;

@RequiredArgsConstructor
@RequestMapping("/api/admins")
@RestController
public class AdminController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        authService.login(loginRequestDto, session);
        return ResponseEntity.ok().build();
    }
}
