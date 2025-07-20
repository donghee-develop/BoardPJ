package com.test.board.domain.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.board.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByIsActiveTrueOrderByNameAsc();
}
