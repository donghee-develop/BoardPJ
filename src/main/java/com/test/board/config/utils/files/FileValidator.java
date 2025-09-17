package com.test.board.config.utils.files;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileValidator {
    /**
     * 이 Validator가 특정 타입을 지원하는지 확인
     *
     * @param type 게시판 타입 등 구분자
     * @return 지원하면 true
     */
    boolean supports(ValidationType type);

    /**
     * 실제 파일 검증 로직 수행
     *
     * @param files 업로드된 파일 리스트
     */
    void validate(List<MultipartFile> files);
}
