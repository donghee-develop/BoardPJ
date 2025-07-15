package com.test.board.config.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.test.board.domain.user.entity.Role;
import com.test.board.domain.user.repository.RoleRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        for (Role.Roles roleEnum : Role.Roles.values()) {
            boolean exists = roleRepository.existsByName(roleEnum);
            if (!exists) {
                Role role = new Role();
                role.updateName(roleEnum);
                roleRepository.save(role);
            }
        }
    }
}
