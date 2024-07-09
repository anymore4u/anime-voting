package com.example.animevoting.adapters.in.web;

import com.example.animevoting.application.AnimeService;
import com.example.animevoting.domain.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private AnimeService animeService;

    @GetMapping("/test/animes")
    public List<Anime> getAnimes() {
        return animeService.getAllAnimes();
    }
}
