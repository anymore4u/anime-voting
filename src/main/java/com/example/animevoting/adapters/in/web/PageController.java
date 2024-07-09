package com.example.animevoting.adapters.in.web;

import com.example.animevoting.application.AnimeService;
import com.example.animevoting.application.VoteService;
import com.example.animevoting.domain.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private AnimeService animeService;

    @Autowired
    private VoteService voteService;

    @GetMapping("/vote")
    public String votePage(Model model) {
        List<Anime> animes = animeService.getAllAnimes();
        model.addAttribute("animes", animes);
        return "vote";
    }

    @GetMapping("/vote-name")
    public String voteNamePage(@RequestParam String animeId, Model model) {
        model.addAttribute("animeId", animeId);
        return "vote-name";
    }

    @PostMapping("/submit-vote")
    public String submitVote(@RequestParam String animeId, @RequestParam String name) {
        boolean success = voteService.vote(animeId, name, null);  // IP address is set to null
        if (success) {
            return "vote-success";
        } else {
            return "vote-failure";
        }
    }
}
