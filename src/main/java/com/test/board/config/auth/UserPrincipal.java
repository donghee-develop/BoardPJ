package com.test.board.config.auth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal {
    private String email;
    private String name;
    private String role;
}
