package com.douzone.pingpong.service.post;

import com.douzone.pingpong.domain.post.Post;
import com.douzone.pingpong.domain.post.Post2;
import com.douzone.pingpong.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void writePost(Post post) {
        postRepository.writePost(post);
    }

    public List<Map<String,Object>> getPostList(Long partId) {
        return postRepository.getPostList(partId);
    }

    @Transactional
    public void delPost(Long postId) {
        postRepository.delPost(postId);
    }

    @Transactional
    public void addPost(Post2 vo) {
        postRepository.addPost(vo);
    }

    public Post2 getPostById(Long postId) {
        return postRepository.getPostById(postId);
    }

    @Transactional
    public void updatePost(Post2 vo) {
        postRepository.updatePost(vo);
    }

    public List<Map<String, Object>> searchPost(String keyword, String partId, Long teamId) {
        return postRepository.searchPost(keyword,partId,teamId);
    }

    public void readPost(Long id, Long postId) {
        postRepository.readPost(id, postId);
    }
}
