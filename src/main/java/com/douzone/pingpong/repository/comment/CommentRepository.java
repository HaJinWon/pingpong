package com.douzone.pingpong.repository.comment;

import com.douzone.pingpong.domain.comment.Comment2;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final SqlSession sqlSession;
    public List<Map<String,Object>> getCommentList(Long postId) {
        List<Map<String,Object>> result = sqlSession.selectList("part.getCommentList",postId);
        return result;

    }
    public void addComment(Comment2 vo) {
        sqlSession.insert("part.addComment",vo);
    }

    public void delComment(Long commentId) {
        sqlSession.delete("part.delComment",commentId);
    }


}
