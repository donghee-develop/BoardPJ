package com.test.board.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.test.board.domain.board.entity.Board;
import com.test.board.domain.post.entity.Post;
import com.test.board.domain.user.entity.User;

@Getter
@AllArgsConstructor
public class PostPostsRequestDto {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotNull(message = "게시판 ID는 필수입니다.")
    private Long boardId;

    public static Post from(PostPostsRequestDto dto, User user, Board board) {
        return new Post(dto.getTitle(), dto.getContent(), user, board);
    }
}
