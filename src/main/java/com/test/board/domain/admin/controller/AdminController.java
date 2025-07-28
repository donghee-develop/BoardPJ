package com.test.board.domain.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.test.board.domain.auth.service.AuthService;

@RequiredArgsConstructor
@RequestMapping("/api/admins")
@RestController
public class AdminController {
    private final AuthService authService;
}
