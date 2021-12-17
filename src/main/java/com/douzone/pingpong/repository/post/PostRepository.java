package com.douzone.pingpong.repository.post;

import com.douzone.pingpong.domain.post.Post;
import com.douzone.pingpong.domain.post.Post2;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;
    private final SqlSession sqlSession;

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

    public void readPost(Long userId, Long postId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("postId",postId);
        sqlSession.insert("part.readPost",map);
    }
    public List<Map<String, Object>> searchPost(String keyword, String partId, Long teamId) {
        Map<String,Object> map = new HashMap<>();
        map.put("keyword",keyword);
        map.put("partId",partId);
        map.put("teamId",teamId);

        return sqlSession.selectList("part.searchPost",map);
    }

    public void writePost(Post post) {
        em.persist(post);
    }
}
