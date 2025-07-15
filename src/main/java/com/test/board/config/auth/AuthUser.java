package com.test.board.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.test.board.domain.user.entity.Role;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthUser {
    /*
    SUPER_ADMIN, SUB_ADMIN, BOARD_ADMIN, USER
     */
    Role.Roles value() default Role.Roles.USER;
}
