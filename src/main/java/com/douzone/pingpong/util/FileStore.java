package com.douzone.pingpong.util;

import com.douzone.pingpong.domain.file.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    private static final String FORMAT_YYYYMMDD = "yyyy/MM/dd"; // 1)
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(FORMAT_YYYYMMDD);


    // 파일 경로 얻기
    public String getFullPath(String filename) throws IOException{
        return fileDir + filename;
    }

    // 파일 여러개 저장하기
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles)
            throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            } }
        return storeFileResult;
    }

    // 파일 1개 저장하기
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException
    {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return UploadFile.makeUploadFile(
                    originalFilename,
                    storeFileName,
                    getFullPath(storeFileName)
                );
    }

    /**
     * 서버에서 관리할 파일명 생성
     * 고객 : a.pnf
     * 서버 : 3195c35-afg4-df5f668d-3513.png
     */
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    /**
     * 파일명에서 확장자만 떼어내는 메소드
     * apple.png  =>  png
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
