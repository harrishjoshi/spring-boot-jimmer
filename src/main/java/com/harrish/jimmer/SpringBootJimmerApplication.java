package com.harrish.jimmer;

import org.babyfish.jimmer.spring.repository.EnableJimmerRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJimmerRepositories(basePackages = "com.harrish.jimmer.**.repositories")
public class SpringBootJimmerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJimmerApplication.class, args);
    }

}