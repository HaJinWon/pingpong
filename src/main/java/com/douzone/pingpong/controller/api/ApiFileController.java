package com.douzone.pingpong.controller.api;

import com.douzone.pingpong.controller.api.dto.file.UploadFileResponse;
import com.douzone.pingpong.domain.file.UploadFile;
import com.douzone.pingpong.util.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class ApiFileController {
    private final FileStore fileStore;

    @PostMapping("/upload")
    public UploadFileResponse upload(
            @RequestParam(value = "file") MultipartFile file
    ) throws IOException {

        UploadFile uploadFile = fileStore.storeFile(file);

        return new UploadFileResponse(uploadFile);
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws
            IOException {
        System.out.println("file:" + fileStore.getFullPath(filename));
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }
}
