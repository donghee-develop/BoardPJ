package com.test.board.domain.post.service;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.test.board.config.error.CustomException;
import com.test.board.config.error.ErrorCode;
import com.test.board.domain.auth.UserPrincipal;
import com.test.board.domain.board.entity.Board;
import com.test.board.domain.board.repository.BoardRepository;
import com.test.board.domain.post.dto.request.GetPostsRequestDto;
import com.test.board.domain.post.dto.request.PostPostsRequestDto;
import com.test.board.domain.post.dto.response.GetPostsResponseDto;
import com.test.board.domain.post.dto.response.PageResponse;
import com.test.board.domain.post.entity.Post;
import com.test.board.domain.post.repository.PostRepository;
import com.test.board.domain.user.entity.User;
import com.test.board.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public PageResponse<GetPostsResponseDto> getPosts(
            GetPostsRequestDto getPostsRequestDto, Pageable pageable) {
        return postRepository.getPosts(getPostsRequestDto, pageable);
    }

    @Transactional
    public void write(PostPostsRequestDto postPostsRequestDto, UserPrincipal userPrincipal) {
        User user =
                userRepository
                        .findById(userPrincipal.getId())
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Board board =
                boardRepository
                        .findById(postPostsRequestDto.getBoardId())
                        .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        Post post = PostPostsRequestDto.from(postPostsRequestDto, user, board);
        postRepository.save(post);
    }
}
