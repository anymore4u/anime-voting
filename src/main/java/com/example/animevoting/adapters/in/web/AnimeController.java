package com.example.animevoting.adapters.in.web;

import com.example.animevoting.application.AnimeService;
import com.example.animevoting.application.VoteService;
import com.example.animevoting.domain.Anime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/animes")
public class AnimeController {

    private final AnimeService animeService;
    private final VoteService voteService;

    public AnimeController(AnimeService animeService, VoteService voteService) {
        this.animeService = animeService;
        this.voteService = voteService;
    }

    @GetMapping
    public List<Anime> getAllAnimes() {
        return animeService.getAllAnimes();
    }

    @GetMapping("/votes")
    public Map<String, Long> getAnimeVotes() {
        return voteService.countVotesByAnime();
    }

    @GetMapping("/update")
    public String updateAnimes() {
        animeService.fetchAndStoreAnimeData();
        return "Anime data updated successfully.";
    }

    @GetMapping("/trigger-update")
    public String triggerUpdateAnimes() {
        animeService.triggerUpdateAnimeData();
        return "Scheduled anime data update triggered successfully.";
    }
}
