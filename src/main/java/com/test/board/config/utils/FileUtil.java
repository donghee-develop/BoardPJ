package com.test.board.config.utils;

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

@Component
@RequiredArgsConstructor
public class FileUtil {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<UploadFile> uploadFiles(List<MultipartFile> multipartFiles) {
        List<UploadFile> uploadedFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                uploadedFiles.add(uploadFile(multipartFile));
            }
        }
        return uploadedFiles;
    }
    
    private UploadFile uploadFile(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String savedName = createSavedFilename(originalFilename);

        try {
            // 지정된 경로에 파일 저장
            multipartFile.transferTo(new File(uploadDir + savedName));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        return new UploadFile(originalFilename, savedName);
    }

    private String createSavedFilename(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
