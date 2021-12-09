package com.douzone.pingpong.repository.part;

import com.douzone.pingpong.domain.post.Comment2;
import com.douzone.pingpong.domain.post.Part;
import com.douzone.pingpong.domain.post.Part2;
import com.douzone.pingpong.domain.post.Post2;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PartRepository {

    @Autowired
    private SqlSession sqlSession;

    public List<Part2> getPartList(Long teamId) {

        return sqlSession.selectList("part.getPartList",teamId);
    }

    public void addPart(Long teamId, String partName) {
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",teamId);
        map.put("partName",partName);

        sqlSession.insert("part.addPart", map);
    }

    public void delPart( Long partId) {

        sqlSession.delete("part.delPart",partId);
    }


    /*============================== Post ===================================================*/

    public List<Map<String,Object>> getPostList(Long partId) {
        List<Map<String,Object>> list = sqlSession.selectList("part.getPostList",partId);
        return list;
    }

    public void delPost(Long postId) {
        sqlSession.delete("part.delPost",postId);
    }

    public void addPost(Post2 vo) {
        sqlSession.insert("part.addPost",vo);
    }

    public Post2 getPostById(Long postId) {
        return sqlSession.selectOne("part.getPostById",postId);
    }

    public void updatePost(Post2 vo) {
        sqlSession.update("part.updatePost",vo);
    }

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

    public List<Map<String, Object>> searchPost(String keyword, String partId, Long teamId) {
        Map<String,Object> map = new HashMap<>();
        map.put("keyword",keyword);
        map.put("partId",partId);
        map.put("teamId",teamId);

        return sqlSession.selectList("part.searchPost",map);
    }

    public Long getFirstPartId(Long teamId) {
        return sqlSession.selectOne("part.getFirstPartId",teamId);
    }

    public void readPost(Long userId, Long postId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("postId",postId);
        sqlSession.insert("part.readPost",map);
    }

    public List<Map<String, Object>> getPostReadMemberList(Long postId) {
        return sqlSession.selectList("part.getPostReadMemberList",postId);
    }
}
