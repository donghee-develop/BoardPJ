package com.test.board.config.auth;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.test.board.domain.user.entity.Role;
import com.test.board.domain.user.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserPrincipal implements Serializable {
    private Long id;
    private String email;
    private String name;
    private Set<Role.Roles> roles;

    public static UserPrincipal from(User user) {
        Set<Role.Roles> roleNames =
                user.getUserRoles().stream()
                        .map(userBoardRole -> userBoardRole.getRole().getName())
                        .collect(Collectors.toSet());

        return new UserPrincipal(user.getId(), user.getEmail(), user.getName(), roleNames);
    }
}
