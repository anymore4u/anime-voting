package com.example.animevoting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class AnimeVotingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimeVotingApplication.class, args);
    }
}
