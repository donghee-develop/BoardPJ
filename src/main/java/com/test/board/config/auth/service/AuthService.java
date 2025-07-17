package com.test.board.config.auth.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.test.board.config.auth.UserPrincipal;
import com.test.board.config.auth.dto.request.LoginRequestDto;
import com.test.board.config.error.CustomException;
import com.test.board.config.error.ErrorCode;
import com.test.board.domain.user.entity.User;
import com.test.board.domain.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpSession session) {
        User user =
                userRepository
                        .findByEmail(loginRequestDto.getEmail())
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }
        UserPrincipal userPrincipal = UserPrincipal.from(user);
        session.setAttribute("user", userPrincipal);
    }
}
