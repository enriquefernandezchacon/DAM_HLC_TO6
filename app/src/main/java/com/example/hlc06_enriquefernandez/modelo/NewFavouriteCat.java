package com.example.hlc06_enriquefernandez.modelo;

import com.google.gson.annotations.SerializedName;

public class NewFavouriteCat {

    @SerializedName("image_id")
    private final String imageId;

    @SerializedName("sub_id")
    private final String subId;

    public NewFavouriteCat(String imageId) {
        this.imageId = imageId;
        this.subId = "enriquefernandez";
    }
}
