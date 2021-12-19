package com.douzone.pingpong.domain.file;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.domain.post.Post;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "upload_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_file_id")
    private Long id;

    @Column(nullable = false)
    private String origFilename;    //사용자가 업로드한 파일명

    @Column(nullable = false)
    private String filename;        // 서버 내부에서 관리하는 파일명

    @Column(nullable = false)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

//    @OneToOne(mappedBy = "uploadFile", fetch = FetchType.LAZY)
//    private Member member;

    public static UploadFile makeUploadFile(String origFilename, String filename, String filePath) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setOrigFilename(origFilename);
        uploadFile.setFilename(filename);
        uploadFile.setFilePath(filePath);

        return uploadFile;
    }

}