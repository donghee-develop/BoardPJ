package com.test.board.domain.post.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.test.board.config.error.CustomException;
import com.test.board.config.error.ErrorCode;
import com.test.board.config.utils.files.UploadFile;
import com.test.board.config.utils.files.FileUtil;
import com.test.board.config.utils.files.FileValidator;
import com.test.board.config.utils.files.FileValidatorFactory;
import com.test.board.config.utils.files.ValidationType;
import com.test.board.domain.auth.UserPrincipal;
import com.test.board.domain.board.entity.Board;
import com.test.board.domain.board.repository.BoardRepository;
import com.test.board.domain.post.dto.request.GetPostsRequestDto;
import com.test.board.domain.post.dto.request.PostPostsRequestDto;
import com.test.board.domain.post.dto.response.GetPostsResponseDto;
import com.test.board.domain.post.dto.response.PageResponse;
import com.test.board.domain.post.entity.Post;
import com.test.board.domain.post.entity.PostFiles;
import com.test.board.domain.post.repository.PostFileRepository;
import com.test.board.domain.post.repository.PostRepository;
import com.test.board.domain.user.entity.User;
import com.test.board.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final PostFileRepository postFileRepository;
    private final FileUtil fileUtil;
    private final FileValidatorFactory fileValidatorFactory;

    @Transactional
    public PageResponse<GetPostsResponseDto> getPosts(
            GetPostsRequestDto getPostsRequestDto, Pageable pageable) {
        return postRepository.getPosts(getPostsRequestDto, pageable);
    }

    @Transactional
    public void write(
            PostPostsRequestDto postPostsRequestDto,
            List<MultipartFile> files,
            UserPrincipal userPrincipal) {
        User user =
                userRepository
                        .findById(userPrincipal.getId())
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Board board =
                boardRepository
                        .findById(postPostsRequestDto.getBoardId())
                        .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        Post post = PostPostsRequestDto.from(postPostsRequestDto, user, board);
        Post savedPost = postRepository.save(post);

        if (files != null && !files.stream().allMatch(MultipartFile::isEmpty)) {
            FileValidator validator = fileValidatorFactory.getValidator(ValidationType.POST);
            validator.validate(files);
            List<UploadFile> uploadedFiles = fileUtil.uploadFiles(files);
            savePostFiles(savedPost, uploadedFiles);
        }
    }

    private void savePostFiles(Post post, List<UploadFile> uploadedFiles) {
        List<PostFiles> postFiles =
                uploadedFiles.stream()
                        .map(
                                uploadFile ->
                                        PostFiles.builder()
                                                .post(post)
                                                .originalName(uploadFile.originalFilename())
                                                .savedName(uploadFile.savedName())
                                                .path(uploadFile.path())
                                                .build())
                        .collect(Collectors.toList());

        postFileRepository.saveAll(postFiles);
    }
}
