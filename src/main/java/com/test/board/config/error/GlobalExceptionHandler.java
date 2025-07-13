package com.test.board.config.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 1. CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(ex.getErrorCode().getStatus())
                .body(ErrorResponseDto.from(ex.getErrorCode(), request.getRequestURI()));
    }

    // ✅ 2. @Valid 검증 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex,
                                                                      HttpServletRequest request) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponseDto.from(ex, request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleInternalException(Exception ex,
                                                                    HttpServletRequest request) {
        log.error("internal server error: {}", ex.getMessage(), ex);

        return ResponseEntity
                .badRequest()
                .body(ErrorResponseDto.from(ErrorCode.INTERNAL_SERVER_ERROR, request.getRequestURI()));
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseDto> handleMaxSizeException(MaxUploadSizeExceededException ex,
                                                                   HttpServletRequest request) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponseDto.from(ex, request.getRequestURI()));
    }

}
