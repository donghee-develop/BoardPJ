package com.test.board.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.test.board.domain.board.dto.request.GetBoardsRequestDto;
import com.test.board.domain.board.entity.Board;

public interface BoardQueryRepository {
    Page<Board> search(GetBoardsRequestDto getBoardsRequestDto, Pageable pageable);
}
