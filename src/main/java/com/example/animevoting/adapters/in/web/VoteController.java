package com.example.animevoting.adapters.in.web;

import com.example.animevoting.application.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/votes")
public class VoteController {
/*
    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/submit-vote")
    public ResponseEntity<?> submitVote(@RequestBody Map<String, String> payload) {
        String animeId = payload.get("animeId");
        String userId = payload.get("userId");

        boolean success = voteService.vote(animeId, userId);
        if (success) {
            return ResponseEntity.ok().body(Map.of("message", "Voto registrado com sucesso!"));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Usuário já votou."));
        }
    }


 */
}
