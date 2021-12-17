package com.douzone.pingpong.service.comment;

import com.douzone.pingpong.domain.comment.Comment2;
import com.douzone.pingpong.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Map<String,Object>> getCommentList(Long postId) {
        return commentRepository.getCommentList(postId);
    }

    public void addComment(Comment2 vo) {
        commentRepository.addComment(vo);
    }

    public void deleteComment(Long commentId) {
        commentRepository.delComment(commentId);
    }
}
