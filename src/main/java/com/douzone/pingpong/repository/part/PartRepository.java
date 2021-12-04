package com.douzone.pingpong.repository.part;

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

        sqlSession.insert("part.addPart",map);
    }

    public void delPart( Long partId) {

        sqlSession.delete("part.delPart",partId);
    }


    /*============================== Post ===================================================*/

    public List<Post2> getPostList(Long partId) {

        return sqlSession.selectList("part.getPostList",partId);
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

    public List<Object> getCommentList(Long postId) {
        return sqlSession.selectList("part.getCommentList",postId);
    }
}
