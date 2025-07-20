package com.test.board.domain.post.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;

import com.test.board.domain.post.entity.Post;

@Getter
public class GetPostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writerNickname;
    private LocalDateTime createdAt;

    // 생성자, getter, setter 등 생략

    public static GetPostsResponseDto from(Post post) {
        GetPostsResponseDto dto = new GetPostsResponseDto();
        dto.id = post.getId();
        dto.title = post.getTitle();
        dto.content = post.getContent();
        dto.writerNickname = post.getUser().getName();
        dto.createdAt = post.getCreatedAt();
        return dto;
    }
}
