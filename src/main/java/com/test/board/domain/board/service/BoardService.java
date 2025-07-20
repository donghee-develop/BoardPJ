package com.test.board.domain.board.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.test.board.domain.board.dto.response.GetBoardsResponseDto;
import com.test.board.domain.board.entity.Board;
import com.test.board.domain.board.repository.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public List<GetBoardsResponseDto> getBoards() {
        List<Board> boards = boardRepository.findByIsActiveTrueOrderByNameAsc();

        return boards.stream().map(GetBoardsResponseDto::from).collect(Collectors.toList());
    }
}
