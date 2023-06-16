package com.example.pokedex.entities;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public class StartResponse {
    private String name;
    private int id;
    private String spriteUrl;
    private Bitmap spriteImg;

    private StartResponse() {
    }

    public StartResponse(String name, int id, String spriteUrl, Bitmap spriteImg) {
        this.name = name;
        this.id = id;
        this.spriteUrl = spriteUrl;
        this.spriteImg = spriteImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpriteUrl() {
        return spriteUrl;
    }

    public void setSpriteUrl(String spriteUrl) {
        this.spriteUrl = spriteUrl;
    }

    public Bitmap getSpriteImg() {
        return spriteImg;
    }

    public void setSpriteImg(Bitmap spriteImg) {
        this.spriteImg = spriteImg;
    }

    @Override
    public String toString() {
        return "StartResponse{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", spriteUrl='" + spriteUrl + '\'' +
                ", spriteImg=" + spriteImg +
                '}';
    }
}
