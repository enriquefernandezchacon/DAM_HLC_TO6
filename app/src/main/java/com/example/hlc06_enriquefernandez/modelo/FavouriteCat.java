package com.example.hlc06_enriquefernandez.modelo;

import com.google.gson.annotations.SerializedName;

public class FavouriteCat {

    @SerializedName("id")
    private String id;

    @SerializedName("image")
    private final CatImage catImage;

    public FavouriteCat(String id, CatImage catImage) {
        this.id = id;
        this.catImage = catImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CatImage getCatImage() {
        return catImage;
    }

    public String getUrl() {
        return catImage.getUrl();
    }

    public class CatImage {
        @SerializedName("id")
        private String id;

        @SerializedName("url")
        private final String url;

        public CatImage(String id, String url) {
            this.id = id;
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }
    }
}
