package com.gx.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS user (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(50),
                    password VARCHAR(100),
                    email VARCHAR(100)
                )
                """;
        try {
            jdbcTemplate.execute(createTableSQL);
            System.out.println(">>> [SUCCESS] user 表创建成功或已存在！");
        } catch (Exception e) {
            System.out.println(">>> [ERROR] 建表失败：" + e.getMessage());
        }
    }
}