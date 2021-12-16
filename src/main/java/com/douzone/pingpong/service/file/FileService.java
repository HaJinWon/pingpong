package com.douzone.pingpong.service.file;


import com.douzone.pingpong.domain.file.UploadFile;
import com.douzone.pingpong.domain.file.UploadFileDto;
import com.douzone.pingpong.repository.file.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService {
    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public Long saveFile(UploadFile uploadFile) {
        return fileRepository.save(uploadFile).getId();
    }

    @Transactional
    public UploadFileDto getFile(Long id) {
        UploadFile file = fileRepository.findById(id).get();

        UploadFileDto fileDto = UploadFileDto.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDto;
    }
}