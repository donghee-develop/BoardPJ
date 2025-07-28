package com.test.board.domain.auth.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import com.test.board.domain.auth.UserPrincipal;
import com.test.board.domain.auth.dto.request.LoginRequestDto;
import com.test.board.domain.auth.service.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Log4j2
public class AuthController {
    private final AuthService authService;

    @PostMapping("/users/login")
    public ResponseEntity<Void> loginUser(
            @Valid @RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        authService.login(loginRequestDto, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admins/login")
    public ResponseEntity<Void> loginAdmin(
            @RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        authService.login(loginRequestDto, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    // 테스트 메소드
    @GetMapping("/check-role")
    public ResponseEntity<String> checkRole(HttpSession session) {
        UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute("user");
        if (userPrincipal == null) {
            log.info("No user in session.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user logged in");
        }

        log.info("User email: {}, Roles: {}", userPrincipal.getEmail(), userPrincipal.getRoles());
        return ResponseEntity.ok("User roles logged");
    }
}
