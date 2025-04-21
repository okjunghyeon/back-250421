package com.ll.back250421.domain.post.post.repository;

import com.ll.back250421.domain.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
