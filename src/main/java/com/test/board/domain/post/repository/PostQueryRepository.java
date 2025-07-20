package com.test.board.domain.post.repository;

import org.springframework.data.domain.Pageable;

import com.test.board.domain.post.dto.request.GetPostsRequestDto;
import com.test.board.domain.post.dto.response.GetPostsResponseDto;
import com.test.board.domain.post.dto.response.PageResponse;

public interface PostQueryRepository {
    PageResponse<GetPostsResponseDto> getPosts(GetPostsRequestDto dto, Pageable pageable);
}
