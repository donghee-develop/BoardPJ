package com.test.board.domain.post.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.board.domain.post.dto.request.GetPostsRequestDto;
import com.test.board.domain.post.dto.response.GetPostsResponseDto;
import com.test.board.domain.post.dto.response.PageResponse;
import com.test.board.domain.post.entity.Post;
import com.test.board.domain.post.entity.QPost;
import com.test.board.domain.user.entity.QUser;

@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public PageResponse<GetPostsResponseDto> getPosts(GetPostsRequestDto dto, Pageable pageable) {
        QPost post = QPost.post;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(post.deleted.isFalse());

        if (dto.getQuery() != null && dto.getKeyword() != null && !dto.getKeyword().isBlank()) {
            switch (dto.getQuery()) {
                case "title" -> builder.and(post.title.containsIgnoreCase(dto.getKeyword()));
                case "content" -> builder.and(post.content.containsIgnoreCase(dto.getKeyword()));
                case "writer" -> builder.and(post.user.name.containsIgnoreCase(dto.getKeyword()));
            }
        }

        if (dto.getStart() != null && dto.getEnd() != null) {
            LocalDateTime start = dto.getStart().toLocalDate().atStartOfDay();
            LocalDateTime end = dto.getEnd().toLocalDate().atTime(LocalTime.MAX);
            builder.and(post.createdAt.between(start, end));
        }

        List<Post> content =
                queryFactory
                        .selectFrom(post)
                        .leftJoin(post.user, QUser.user)
                        .fetchJoin()
                        .where(builder)
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(post.createdAt.desc())
                        .fetch();

        Long total = queryFactory.select(post.count()).from(post).where(builder).fetchOne();

        List<GetPostsResponseDto> dtoList =
                content.stream().map(GetPostsResponseDto::from).toList();

        return new PageResponse<>(
                dtoList,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                total == null ? 0 : total);
    }
}
