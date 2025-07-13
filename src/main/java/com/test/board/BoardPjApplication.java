package com.test.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class BoardPjApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardPjApplication.class, args);
    }

}
