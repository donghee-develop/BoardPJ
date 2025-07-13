package com.test.board.domain.user.controller;

import com.test.board.config.auth.RoleUser;
import com.test.board.domain.user.dto.request.LoginUserDto;
import com.test.board.domain.user.dto.request.SignUpUserDto;
import com.test.board.domain.user.entity.User;
import com.test.board.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginUserDto loginUserDto
            ){
        userService.login(loginUserDto);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(
            @Valid @RequestBody SignUpUserDto signUpUserDto
    ){
        userService.signUp(signUpUserDto);
        return ResponseEntity.ok().build();
    }
}
