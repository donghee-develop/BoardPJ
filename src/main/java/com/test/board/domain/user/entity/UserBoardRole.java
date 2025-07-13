package com.test.board.domain.user.entity;

import com.test.board.config.BaseEntity;
import com.test.board.domain.board.entity.Board;
import jakarta.persistence.*;

@Entity
@Table(name = "user_board_roles")
public class UserBoardRole extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Enumerated(EnumType.STRING)
    private Role.Roles role;

}
