package com.example.pokedex.models;

import com.example.pokedex.entities.StartResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class PokemonRepository {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).connectTimeout(30, TimeUnit.SECONDS).build();

    public void getBasicResponse(String apiUrl, Callback callback) {
        Request request = new Request.Builder().url(apiUrl).build();
        client.newCall(request).enqueue(callback);
    }

    public void getPartialResponse(String pokemonUrl, Callback callback) {
        Request request = new Request.Builder().url(pokemonUrl).build();
        client.newCall(request).enqueue(callback);
    }

    public void getCompleteResponse(StartResponse startResponse, Callback callback) {
        Request request = new Request.Builder().url(startResponse.getSpriteUrl()).build();
        client.newCall(request).enqueue(callback);
    }
}
