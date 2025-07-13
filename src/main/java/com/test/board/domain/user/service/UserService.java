package com.test.board.domain.user.service;

import com.test.board.config.error.CustomException;
import com.test.board.config.error.ErrorCode;
import com.test.board.domain.user.dto.request.LoginUserDto;
import com.test.board.domain.user.entity.User;
import com.test.board.domain.user.mapper.UserMapper;
import com.test.board.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public void login(LoginUserDto loginUserDto) {
        User user = userRepository
                .findByEmail(loginUserDto
                        .getEmail()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }


    }
}
