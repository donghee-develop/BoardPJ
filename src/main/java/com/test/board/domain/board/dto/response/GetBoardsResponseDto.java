package com.test.board.domain.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.test.board.domain.board.entity.Board;

@Getter
@AllArgsConstructor
public class GetBoardsResponseDto {
    private String name;

    public static GetBoardsResponseDto from(Board board) {
        return new GetBoardsResponseDto(board.getName());
    }
}
