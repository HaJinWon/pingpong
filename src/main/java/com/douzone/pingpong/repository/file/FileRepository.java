package com.douzone.pingpong.repository.file;

import com.douzone.pingpong.domain.file.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}