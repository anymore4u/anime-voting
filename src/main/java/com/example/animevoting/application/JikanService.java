package com.example.animevoting.application;

import com.example.animevoting.domain.Anime;
import com.example.animevoting.domain.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JikanService {

    private final RestTemplate restTemplate;

    @Autowired
    public JikanService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("animes")
    @Retryable(
            value = {org.springframework.web.client.HttpClientErrorException.TooManyRequests.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 3000, multiplier = 2)
    )
    public List<Anime> getAnimesFromApi() {
        String apiUrl = "https://api.jikan.moe/v4/seasons/now?sfw";
        List<Anime> allAnimes = new ArrayList<>();
        int currentPage = 1;
        boolean hasNextPage;

        do {
            String pageUrl = apiUrl + "?page=" + currentPage;
            ApiResponse response = restTemplate.getForObject(pageUrl, ApiResponse.class);

            if (response != null && response.getData() != null) {
                allAnimes.addAll(response.getData());
                hasNextPage = response.getPagination().isHasNextPage();
                currentPage++;
            } else {
                hasNextPage = false;
            }

            // Adiciona um delay entre as requisições para evitar a limitação de taxa
            try {
                Thread.sleep(2000);  // Delay de 2 segundos entre as requisições
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } while (hasNextPage);

        return allAnimes.stream().distinct().collect(Collectors.toList());
    }
}
