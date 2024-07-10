package com.example.animevoting.adapters.out.mongodb;

import com.example.animevoting.domain.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VoteRepository extends MongoRepository<Vote, String> {
    List<Vote> findByUserId(String userId);
}
