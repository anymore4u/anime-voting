package com.example.animevoting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class AnimeVotingApplication {
    public static void main(String[] args) {
        // Carregar variáveis de ambiente do arquivo .env
        Dotenv dotenv = Dotenv.load();
        System.setProperty("spring.data.mongodb.uri", dotenv.get("MONGO_URL"));

        SpringApplication.run(AnimeVotingApplication.class, args);
    }
}
