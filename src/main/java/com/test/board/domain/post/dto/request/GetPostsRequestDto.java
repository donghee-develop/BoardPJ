package com.test.board.domain.post.dto.request;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetPostsRequestDto {
    private String query;
    private String keyword;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long boardId;

    private Integer page;
    private Integer size;

    public Pageable toPageable() {
        int p = (page != null && page > 0) ? page - 1 : 0;
        int s = (size != null && size > 0) ? size : 10;
        return PageRequest.of(p, s, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
