package com.test.board.config.utils.files;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.test.board.config.error.CustomException;
import com.test.board.config.error.ErrorCode;

@Component
public class PostFileValidator implements FileValidator {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 20MB
    private static final List<String> ALLOWED_MIME_TYPES =
            List.of("image/jpeg", "image/png", "image/gif");

    @Override
    public boolean supports(ValidationType type) {
        return ValidationType.POST == type;
    }

    @Override
    public void validate(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) return;

        for (MultipartFile file : files) {
            if (file.getSize() > MAX_FILE_SIZE) {
                throw new CustomException(ErrorCode.FILE_SIZE_EXCEEDED);
            }
            if (!ALLOWED_MIME_TYPES.contains(file.getContentType())) {
                throw new CustomException(ErrorCode.INVALID_MIME_TYPE);
            }
        }
    }
}
