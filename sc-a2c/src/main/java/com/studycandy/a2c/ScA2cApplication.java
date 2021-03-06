package com.studycandy.a2c;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Coding with Intellij IDEA
 * Author: Chenls
 * Time: 2017/3/24
 */
@SpringBootApplication
@EnableWebMvc
@MapperScan(basePackages = {
        "com.studycandy.core.mybatis",
        "com.studycandy.a2c.mapper",
        "com.studycandy.a2c.session.mapper"
})
public class ScA2cApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ScA2cApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ScA2cApplication.class);
    }
}
