package com.test.board.domain.user.entity;

import jakarta.persistence.*;

import lombok.Getter;

import com.test.board.config.BaseEntity;
import com.test.board.domain.board.entity.Board;

@Entity
@Table(name = "user_board_roles")
@Getter
public class UserBoardRole extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public static UserBoardRole from(User user, Board board, Role role) {
        UserBoardRole userBoardRole = new UserBoardRole();
        userBoardRole.user = user;
        userBoardRole.board = board;
        userBoardRole.role = role;

        user.addUserBoardRole(userBoardRole);

        return userBoardRole;
    }
}
