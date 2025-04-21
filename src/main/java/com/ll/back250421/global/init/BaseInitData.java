package com.ll.back250421.global.initData;

import com.ll.back250421.domain.post.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class BaseInitData {

    private final PostService postService;

    @Autowired
    @Lazy
    private BaseInitData self;

    public BaseInitData(PostService postService) {
        this.postService = postService;
    }

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> self.work1();
    }

    @Transactional
    public void work1() {
        if (postService.count() > 0) return;

        postService.write("제목 1", "내용 1");
        postService.write("제목 2", "내용 2");

        self.get1Plus1();
        self.get1Plus1();
        self.get1Plus1();
    }

    @Cacheable("get1Plus1")
    public int get1Plus1() {
        System.out.println("get1Plus1 run!");
        return 1 + 1;
    }
}
