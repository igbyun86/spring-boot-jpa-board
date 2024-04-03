package com.example.springbootjpa.biz.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootTest
public class InitDataInsert {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("사원등록")
    void insert() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("employee.sql");
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jdbcTemplate.execute(line);
                }
            }
        } else {
            throw new IOException("SQL file not found!");
        }
    }


}
