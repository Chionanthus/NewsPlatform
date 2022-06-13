package com.chionanthus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chionanthus.mapper")
public class NewsplatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsplatformApplication.class, args);
    }

}
