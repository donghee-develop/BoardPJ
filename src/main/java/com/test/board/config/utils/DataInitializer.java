package com.test.board.config.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import com.test.board.domain.board.entity.Board;
import com.test.board.domain.board.repository.BoardRepository;
import com.test.board.domain.user.entity.Role;
import com.test.board.domain.user.entity.User;
import com.test.board.domain.user.entity.UserBoardRole;
import com.test.board.domain.user.repository.RoleRepository;
import com.test.board.domain.user.repository.UserBoardRoleRepository;
import com.test.board.domain.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Log4j2
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserBoardRoleRepository userBoardRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;

    @Override
    public void run(String... args) throws Exception {
        for (Role.Roles roleEnum : Role.Roles.values()) {
            boolean exists = roleRepository.existsByName(roleEnum);
            if (!exists) {
                Role role = new Role();
                role.updateName(roleEnum);
                roleRepository.save(role);
                log.info("권한 목록 생성");
            }
        }
        String adminEmail = "test";
        if (!userRepository.existsByEmail(adminEmail)) {
            User admin =
                    User.builder()
                            .email(adminEmail)
                            .name("superadmin")
                            .password(passwordEncoder.encode("test"))
                            .phoneNumber("010-0000-0000")
                            .build();
            userRepository.save(admin);

            Role superAdminRole =
                    roleRepository
                            .findByName(Role.Roles.SUPER_ADMIN)
                            .orElseThrow(() -> new RuntimeException("SUPER_ADMIN 역할이 존재하지 않습니다."));

            UserBoardRole adminRole = UserBoardRole.from(admin, null, superAdminRole);
            userBoardRoleRepository.save(adminRole);
            log.info("슈퍼 관리자 생성");
        }

        if (boardRepository.count() == 0) { // 이미 있으면 안 만듦
            Board foodBoard = new Board("음식", true);
            Board animalBoard = new Board("동물", true);
            Board gameBoard = new Board("게임", true);

            boardRepository.save(foodBoard);
            boardRepository.save(animalBoard);
            boardRepository.save(gameBoard);

            log.info("초기 게시판 3개 생성");
        }
    }
}
