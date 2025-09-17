package com.test.board.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.board.domain.post.entity.PostFiles;

public interface PostFileRepository extends JpaRepository<PostFiles, Long> {}
