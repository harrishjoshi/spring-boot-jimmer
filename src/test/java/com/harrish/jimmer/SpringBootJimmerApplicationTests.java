package com.harrish.jimmer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SpringBootJimmerApplication.class, properties = {"spring.profiles.active=test"})
class SpringBootJimmerApplicationTests {

    @Test
    void contextLoads() {
    }
}