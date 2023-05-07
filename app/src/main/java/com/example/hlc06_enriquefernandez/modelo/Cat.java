package com.example.hlc06_enriquefernandez.modelo;

import com.google.gson.annotations.SerializedName;

public class Cat {

    @SerializedName("id")
    private String id;

    @SerializedName("url")
    private final String url;

    @SerializedName("name")
    private final String name;

    public Cat(String id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
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

    public String getName() {
        return name;
    }
}
