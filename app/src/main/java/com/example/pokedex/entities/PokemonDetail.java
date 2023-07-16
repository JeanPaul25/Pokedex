package com.example.pokedex.entities;

import android.graphics.Bitmap;

import com.google.gson.JsonArray;

import java.util.Arrays;
import java.util.List;

public class PokemonDetail {
    private String name;
    private Integer id;
    private Integer weight;
    private Integer base_experience;
    private Integer height;
    private String spriteUrl;
    private Bitmap spriteImg;
    private JsonArray abilities;

    public PokemonDetail(String name, Integer id, Integer weight, Integer base_experience, Integer height, String spriteUrl, Bitmap spriteImg, JsonArray abilities) {
        this.name = name;
        this.id = id;
        this.weight = weight;
        this.base_experience = base_experience;
        this.height = height;
        this.spriteUrl = spriteUrl;
        this.spriteImg = spriteImg;
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getBase_experience() {
        return base_experience;
    }

    public void setBase_experience(Integer base_experience) {
        this.base_experience = base_experience;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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

    public JsonArray getAbilities() {
        return abilities;
    }

    public void setAbilities(JsonArray abilities) {
        this.abilities = abilities;
    }

    @Override
    public String toString() {
        return "PokemonDetail{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", weight=" + weight +
                ", base_experience=" + base_experience +
                ", height=" + height +
                ", spriteUrl='" + spriteUrl + '\'' +
                ", spriteImg=" + spriteImg +
                ", abilities=" + abilities +
                '}';
    }
}
