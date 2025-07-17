package com.test.board.config.error;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 1. CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(
            CustomException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getErrorCode().getStatus())
                .body(ErrorResponseDto.fromErrorCode(ex.getErrorCode(), request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest()
                .body(ErrorResponseDto.fromValidationException(ex, request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleInternalException(
            Exception ex, HttpServletRequest request) {
        log.error("internal server error: {}", ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDto.fromUnexpectedException(ex, request.getRequestURI()));
    }

    //    @ExceptionHandler(MaxUploadSizeExceededException.class)
    //    public ResponseEntity<ErrorResponseDto>
    // handleMaxSizeException(MaxUploadSizeExceededException ex,
    //                                                                   HttpServletRequest request)
    // {
    //        return ResponseEntity
    //                .badRequest()
    //                .body(ErrorResponseDto.from(ex, request.getRequestURI()));
    //    }

}
