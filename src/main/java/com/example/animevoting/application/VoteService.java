package com.example.animevoting.application;

import com.example.animevoting.adapters.out.mongodb.VoteRepository;
import com.example.animevoting.domain.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public boolean vote(String animeId, String userId, String userIp) {
        // Convert userId to uppercase
        String upperCaseUserId = userId.toUpperCase();

        // Verificar se o usuário já votou em qualquer anime
        List<Vote> existingVotes = voteRepository.findByUserId(upperCaseUserId);
        if (!existingVotes.isEmpty()) {
            // Usuário já votou
            return false;
        }

        // Salvar novo voto
        Vote vote = new Vote(animeId, upperCaseUserId, userIp);
        voteRepository.save(vote);
        return true;
    }

    public Map<String, Long> countVotesByAnime() {
        List<Vote> votes = voteRepository.findAll();
        return votes.stream()
                .collect(Collectors.groupingBy(Vote::getAnimeId, Collectors.counting()));
    }
}
