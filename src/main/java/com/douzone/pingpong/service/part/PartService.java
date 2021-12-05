package com.douzone.pingpong.service.part;

import com.douzone.pingpong.domain.post.Comment2;
import com.douzone.pingpong.domain.post.Part;
import com.douzone.pingpong.domain.post.Part2;
import com.douzone.pingpong.domain.post.Post2;
import com.douzone.pingpong.repository.part.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PartService {

    @Autowired
    private PartRepository partRepository;


    public List<Part2> getPartList(Long teamId) {
        return partRepository.getPartList(teamId);
    }

    public void addPart(Long teamId, String partName) {
        partRepository.addPart(teamId,partName);
    }

    public void delPart(Long partId) {
        partRepository.delPart(partId);
    }
    /* =================================  Post  ===========================================*/
    public List<Map<String,Object>> getPostList(Long partId) {
        return partRepository.getPostList(partId);
    }

    public void delPost(Long postId) {
        partRepository.delPost(postId);
    }


    public void addPost(Post2 vo) {
        partRepository.addPost(vo);
    }

    public Post2 getPostById(Long postId) {
        return partRepository.getPostById(postId);
    }

    public void updatePost(Post2 vo) {
        partRepository.updatePost(vo);
    }

    public List<Map<String,Object>> getCommentList(Long postId) {
        return partRepository.getCommentList(postId);
    }

    public void addComment(Comment2 vo) {
        partRepository.addComment(vo);
    }

    public void deleteComment(Long commentId) {
        partRepository.delComment(commentId);
    }

    public List<Map<String, Object>> searchPost(String keyword, String partId, Long teamId) {
        return partRepository.searchPost(keyword,partId,teamId);
    }

    public Long getFirstPartId(Long teamId) {
        return partRepository.getFirstPartId(teamId);
    }
}
