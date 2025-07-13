package com.test.board.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleUser {
    /*
    SUPER_ADMIN, SUB_ADMIN, BOARD_ADMIN, USER
     */
    String value();
}
