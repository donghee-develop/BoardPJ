package com.test.board.config.utils.files;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.test.board.config.error.CustomException;
import com.test.board.config.error.ErrorCode;

@Component
@RequiredArgsConstructor
public class FileValidatorFactory {

    private final List<FileValidator> validators;

    public FileValidator getValidator(ValidationType type) {
        return validators.stream()
                .filter(validator -> validator.supports(type))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.VALIDATOR_NOT_FOUND));
    }
}
