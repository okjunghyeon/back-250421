package com.ll.back250421;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Back250421Application {

    public static void main(String[] args) {
        SpringApplication.run(Back250421Application.class, args);
    }

}
