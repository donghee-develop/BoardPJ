package com.test.board.domain.user.service;

import com.test.board.config.error.CustomException;
import com.test.board.config.error.ErrorCode;
import com.test.board.domain.user.dto.request.LoginUserDto;
import com.test.board.domain.user.dto.request.SignUpUserDto;
import com.test.board.domain.user.entity.Role;
import com.test.board.domain.user.entity.User;
import com.test.board.domain.user.entity.UserBoardRole;
import com.test.board.domain.user.mapper.UserMapper;
import com.test.board.domain.user.repository.RoleRepository;
import com.test.board.domain.user.repository.UserBoardRoleRepository;
import com.test.board.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserBoardRoleRepository userBoardRoleRepository;
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
    @Transactional
    public void signUp(SignUpUserDto signUpUserDto) {
        if(userRepository.existsByEmail(signUpUserDto.getEmail())){
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        if(userRepository.existsByName(signUpUserDto.getName())){
            throw new CustomException(ErrorCode.DUPLICATED_NAME);
        }

        String encodedPassword = passwordEncoder.encode(signUpUserDto.getPassword());


        User user = User.from(signUpUserDto, encodedPassword);

        userRepository.save(user);

        Role userRole = roleRepository.findByName(Role.Roles.USER)
                .orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND));

        UserBoardRole userBoardRole = UserBoardRole.from(user, null, userRole);
        userBoardRoleRepository.save(userBoardRole);
    }
}
