package com.example.hlc06_enriquefernandez.modelo;

import com.google.gson.annotations.SerializedName;

public class NewFavouriteCatResponse {

    @SerializedName("id")
    private String id;

    NewFavouriteCatResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
