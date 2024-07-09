package com.example.animevoting.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ApiResponse {
    private Pagination pagination;
    private List<Anime> data;

    // Getters and setters

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Anime> getData() {
        return data;
    }

    public void setData(List<Anime> data) {
        this.data = data;
    }

    public static class Pagination {
        @JsonProperty("last_visible_page")
        private int lastVisiblePage;

        @JsonProperty("has_next_page")
        private boolean hasNextPage;

        // Getters and setters

        public int getLastVisiblePage() {
            return lastVisiblePage;
        }

        public void setLastVisiblePage(int lastVisiblePage) {
            this.lastVisiblePage = lastVisiblePage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }
    }
}
