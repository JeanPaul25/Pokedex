package com.example.pokedex.entities;

import android.graphics.Bitmap;

import java.util.Arrays;

public class Favourites {

    Integer id;
    String name;
    byte[] spriteImg;

    public Favourites(Integer id, String name, byte[] spriteImg) {
        this.id = id;
        this.name = name;
        this.spriteImg = spriteImg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getSpriteImg() {
        return spriteImg;
    }

    public void setSpriteImg(byte[] spriteImg) {
        this.spriteImg = spriteImg;
    }

    @Override
    public String toString() {
        return "Favourites{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", spriteImg=" + Arrays.toString(spriteImg) +
                '}';
    }
}
