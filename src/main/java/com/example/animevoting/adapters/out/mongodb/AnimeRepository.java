package com.example.animevoting.adapters.out.mongodb;

import com.example.animevoting.domain.Anime;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimeRepository extends MongoRepository<Anime, String> {
    Anime findByMalId(String malId);
}
