package com.test.board.config.utils.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.test.board.config.error.CustomException;
import com.test.board.config.error.ErrorCode;
import com.test.board.config.utils.UploadFile;

@Component
@RequiredArgsConstructor
public class FileUtil {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final List<String> ALLOWED_MIME_TYPES =
            List.of("image/jpeg", "image/png", "image/gif", "text/plain");

    /*
    파일이 여러 개 일 경우
    uploadFile 메소드 호출
     */
    public List<UploadFile> uploadFiles(List<MultipartFile> multipartFiles) {
        List<UploadFile> uploadedFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                uploadedFiles.add(uploadFile(multipartFile));
            }
        }
        return uploadedFiles;
    }

    /*
    파일의 originalName, saveName 생성 후
    saveName으로 디렉터리에 파일 저장
     */
    private UploadFile uploadFile(MultipartFile multipartFile) {
        // 1. 파일 이름 유효성 검사 및 정리
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_FILENAME);
        }

        // 2. 파일 종류(MIME 타입) 검증
        String contentType = multipartFile.getContentType();
        if (!ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new CustomException(ErrorCode.INVALID_MIME_TYPE);
        }

        // 3. 확장자 추출 및 검증
        String extension = extractExt(originalFilename);
        if (extension.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_FILE_EXTENSION);
        }

        // 4. 고유한 파일 이름 생성
        String savedName = createSavedFilename(extension);
        String fullPath = uploadDir + savedName;

        // 5. 파일 저장
        try {
            multipartFile.transferTo(new File(uploadDir + savedName));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }
        return new UploadFile(originalFilename, savedName, fullPath);
    }

    /*
    UUID 생성
     */
    private String createSavedFilename(String extension) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    private String extractExt(String originalFilename) {
        try {
            return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        } catch (StringIndexOutOfBoundsException e) {
            return ""; // 확장자가 없는 경우 빈 문자열 반환
        }
    }
}
