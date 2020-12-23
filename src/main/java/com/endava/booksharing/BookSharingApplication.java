package com.endava.booksharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@PropertySource("classpath:postgres.properties")
@PropertySource("classpath:messages.properties")
public class BookSharingApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookSharingApplication.class, args);
    }
}
