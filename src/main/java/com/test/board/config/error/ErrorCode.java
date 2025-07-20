package com.test.board.config.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // ✅ 유저 로직 관련 에러
    USER_NOT_FOUND("존재하지 않는 사용자입니다", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH("비밀번호가 일치하지 않습니다", HttpStatus.UNAUTHORIZED),
    DUPLICATED_EMAIL("이미 등록된 이메일입니다.", HttpStatus.CONFLICT),
    DUPLICATED_NAME("이미 존재하는 이름입니다.", HttpStatus.CONFLICT),
    ROLE_NOT_FOUND("해당 권한은 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    DUPLICATED_PHONE_NUMBER("이미 가입된 휴대폰 번호입니다.", HttpStatus.CONFLICT),
    SAME_PASSWORD("이전 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.", HttpStatus.BAD_REQUEST),
    EMPTY_PROFILE_IMAGE("프로필 이미지가 비어 있습니다!", HttpStatus.BAD_REQUEST),
    IMAGE_SAVE_FAIL("이미지 저장에 실패 했습니다!", HttpStatus.INTERNAL_SERVER_ERROR),

    // 메일
    EMAIL_SEND_FAILED("이메일 전송에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 게시판
    BOARD_NOT_FOUND("해당 게시판을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    // 500 서버
    INTERNAL_SERVER_ERROR("처리하지 못한 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;
}
