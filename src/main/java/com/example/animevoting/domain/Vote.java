package com.example.animevoting.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Vote {

    @Id
    private String id;
    private String animeId;
    private String userId;
    private String userIp;

    public Vote() {
    }

    public Vote(String id, String animeId, String userId, String userIp) {
        this.id = id;
        this.animeId = animeId;
        this.userId = userId;
        this.userIp = userIp;
    }

    public Vote(String animeId, String userId, String userIp) {
        this.animeId = animeId;
        this.userId = userId;
        this.userIp = userIp;
    }

    // Getters e Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnimeId() {
        return animeId;
    }

    public void setAnimeId(String animeId) {
        this.animeId = animeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }
}
