package com.example.animevoting.adapters.in.web;

import com.example.animevoting.application.VoteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public String vote(@RequestParam String animeId, @RequestParam String userId, @RequestParam String userIp) {
        boolean success = voteService.vote(animeId, userId, userIp);
        if (success) {
            return "Vote recorded successfully.";
        } else {
            return "You have already voted for this anime.";
        }
    }
}
