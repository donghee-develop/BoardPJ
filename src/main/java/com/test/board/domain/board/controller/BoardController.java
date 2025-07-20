package com.test.board.domain.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.test.board.domain.board.dto.response.GetBoardsResponseDto;
import com.test.board.domain.board.service.BoardService;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<GetBoardsResponseDto>> getBoards() {
        List<GetBoardsResponseDto> res = boardService.getBoards();
        return ResponseEntity.ok(res);
    }
}
