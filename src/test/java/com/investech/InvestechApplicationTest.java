package com.investech;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InvestechApplicationTest {

    @Test
    void contextLoads() {
        // 原有的简单测试
    }

    @Test
    void applicationStartsSuccessfully() {
        InvestechApplication app = new InvestechApplication();
        SpringApplication.run(app.getClass(), new String[]{});
    }
}