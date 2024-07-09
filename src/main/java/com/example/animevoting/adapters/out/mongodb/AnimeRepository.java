package com.example.animevoting.adapters.out.mongodb;

import com.example.animevoting.domain.Anime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends MongoRepository<Anime, String> {
    Anime findByMalId(String malId);
}
