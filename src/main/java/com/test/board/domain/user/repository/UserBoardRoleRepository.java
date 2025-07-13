package com.test.board.domain.user.repository;

import com.test.board.domain.user.entity.UserBoardRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRoleRepository extends JpaRepository<UserBoardRole, Long> {
}
