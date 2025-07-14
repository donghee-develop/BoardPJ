package com.test.board.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.board.domain.user.entity.UserBoardRole;

public interface UserBoardRoleRepository extends JpaRepository<UserBoardRole, Long> {}
