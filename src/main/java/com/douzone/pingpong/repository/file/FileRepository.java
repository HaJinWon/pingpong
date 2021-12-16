package com.douzone.pingpong.repository.file;

import com.douzone.pingpong.domain.file.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<UploadFile, Long> {
}