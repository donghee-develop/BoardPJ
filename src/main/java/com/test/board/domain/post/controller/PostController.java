package com.test.board.domain.post.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.test.board.domain.auth.AuthUser;
import com.test.board.domain.auth.UserPrincipal;
import com.test.board.domain.post.dto.request.GetPostsRequestDto;
import com.test.board.domain.post.dto.request.PostPostsRequestDto;
import com.test.board.domain.post.dto.response.GetPostsResponseDto;
import com.test.board.domain.post.dto.response.PageResponse;
import com.test.board.domain.post.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<PageResponse<GetPostsResponseDto>> getPosts(
            GetPostsRequestDto getPostsRequestDto) {
        {
            Pageable pageable = getPostsRequestDto.toPageable();

            PageResponse<GetPostsResponseDto> res =
                    postService.getPosts(getPostsRequestDto, pageable);
            return ResponseEntity.ok(res);
        }
    }

    @PostMapping("/write")
    public ResponseEntity<Void> writePost(
            @Valid @RequestBody PostPostsRequestDto postPostsRequestDt,
            @AuthUser UserPrincipal userPrincipal) {
        postService.write(postPostsRequestDt, userPrincipal);
        return ResponseEntity.ok().build();
    }
}
