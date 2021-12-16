package com.douzone.pingpong.domain.file;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "upload_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origFilename;    //사용자가 업로드한 파일명

    @Column(nullable = false)
    private String filename;        // 서버 내부에서 관리하는 파일명

    @Column(nullable = false)
    private String filePath;

    public UploadFile(String origFilename, String filename, String filePath) {
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }

}