package com.test.board.domain.user.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.test.board.config.auth.UserPrincipal;
import com.test.board.config.error.CustomException;
import com.test.board.config.error.ErrorCode;
import com.test.board.domain.user.dto.request.LoginRequestDto;
import com.test.board.domain.user.dto.request.SignUpRequestDto;
import com.test.board.domain.user.dto.response.ProfileResponseDto;
import com.test.board.domain.user.entity.Role;
import com.test.board.domain.user.entity.User;
import com.test.board.domain.user.entity.UserBoardRole;
import com.test.board.domain.user.repository.RoleRepository;
import com.test.board.domain.user.repository.UserBoardRoleRepository;
import com.test.board.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserBoardRoleRepository userBoardRoleRepository;
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

    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        if (userRepository.existsByName(signUpRequestDto.getName())) {
            throw new CustomException(ErrorCode.DUPLICATED_NAME);
        }

        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        User user = User.from(signUpRequestDto, encodedPassword);

        userRepository.save(user);

        Role userRole =
                roleRepository
                        .findByName(Role.Roles.USER)
                        .orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND));

        UserBoardRole userBoardRole = UserBoardRole.from(user, null, userRole);
        userBoardRoleRepository.save(userBoardRole);
    }

    public ProfileResponseDto getMe(Long id) {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new ProfileResponseDto(
                user.getEmail(),
                user.getName(),
                user.getProfileImage(),
                user.getPhoneNumber(),
                user.getCreatedAt());
    }
}
