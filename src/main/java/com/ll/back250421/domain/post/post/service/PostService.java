package com.ll.back250421.domain.post.post.service;

import com.ll.back250421.domain.post.post.entity.Post;
import com.ll.back250421.domain.post.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public long count() {
        return postRepository.count();
    }

    public Post write(String title, String content) {
        Post post = new Post(title, content);
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }
}