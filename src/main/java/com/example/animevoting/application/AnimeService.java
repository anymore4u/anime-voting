package com.example.animevoting.application;

import com.example.animevoting.adapters.out.mongodb.AnimeRepository;
import com.example.animevoting.domain.Anime;
import com.example.animevoting.domain.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AnimeService {

    private final RestTemplate restTemplate;
    private final AnimeRepository animeRepository;

    @Autowired
    public AnimeService(RestTemplate restTemplate, AnimeRepository animeRepository) {
        this.restTemplate = restTemplate;
        this.animeRepository = animeRepository;
    }

    @Scheduled(cron = "0 0 22 * * SUN", zone = "America/Sao_Paulo")
    public void updateAnimeData() {
        fetchAndStoreAnimeData();
    }

    public void fetchAndStoreAnimeData() {
        String apiUrl = "https://api.jikan.moe/v4/seasons/now";
        Set<Anime> uniqueAnimes = new HashSet<>();
        int currentPage = 1;
        boolean hasNextPage;

        do {
            String pageUrl = apiUrl + "?page=" + currentPage + "&sfw";
            ApiResponse response = restTemplate.getForObject(pageUrl, ApiResponse.class);

            if (response != null && response.getData() != null) {
                uniqueAnimes.addAll(response.getData());
                hasNextPage = response.getPagination().isHasNextPage();
                currentPage++;
            } else {
                hasNextPage = false;
            }

            try {
                Thread.sleep(2000);  // Delay de 2 segundos entre as requisições
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } while (hasNextPage);

        for (Anime anime : uniqueAnimes) {
            if (animeRepository.findByMalId(anime.getMalId()) == null) {
                animeRepository.save(anime);
            }
        }
    }

    public List<Anime> getAllAnimes() {
        return animeRepository.findAll()
                .stream()
                .sorted(Comparator.comparingDouble(Anime::getScore).reversed())
                .collect(Collectors.toList());
    }
}
