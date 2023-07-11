package com.example.pokedex.entities;

import android.graphics.Bitmap;

public class PokemonResponse {
    private String name;
    private String pokemonUrl;
    private Integer id;
    private String spriteUrl;
    private Bitmap spriteImg;

    public PokemonResponse(String name, String pokemonUrl, Integer id, String spriteUrl, Bitmap spriteImg) {
        this.name = name;
        this.pokemonUrl = pokemonUrl;
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

    public String getPokemonUrl() {
        return pokemonUrl;
    }

    public void setPokemonUrl(String pokemonUrl) {
        this.pokemonUrl = pokemonUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
                ", pokemonUrl='" + pokemonUrl + '\'' +
                ", id=" + id +
                ", spriteUrl='" + spriteUrl + '\'' +
                ", spriteImg=" + spriteImg +
                '}';
    }
}
