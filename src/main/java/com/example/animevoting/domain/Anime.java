package com.example.animevoting.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class Anime {

    @Id
    private String id;
    @JsonProperty("mal_id")
    private String malId;
    private String title;
    private String synopsis;
    private double score;
    private int members;
    private Broadcast broadcast;
    private List<Studio> studios;

    @JsonProperty("images")
    private Images images;

    @JsonProperty("url")
    private String url;

    // Getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public Broadcast getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMalId() {
        return malId;
    }

    public void setMalId(String malId) {
        this.malId = malId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Broadcast {
        private String day;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Images {
        private Webp webp;

        public Webp getWebp() {
            return webp;
        }

        public void setWebp(Webp webp) {
            this.webp = webp;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Webp {
        @JsonProperty("large_image_url")
        private String largeImageUrl;

        public String getLargeImageUrl() {
            return largeImageUrl;
        }

        public void setLargeImageUrl(String largeImageUrl) {
            this.largeImageUrl = largeImageUrl;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Studio {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
