package com.example.pokedex.models;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class PokemonRepository {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).connectTimeout(30, TimeUnit.SECONDS).build();

    public void repoBasicResponse(String apiUrl, Callback callback) {
        Request request = new Request.Builder().url(apiUrl).build();
        client.newCall(request).enqueue(callback);
    }

    public void repoPartialStartResponse(String pokemonUrl, Callback callback) {
        Request request = new Request.Builder().url(pokemonUrl).build();
        client.newCall(request).enqueue(callback);
    }

    public void repoCompleteStartResponse(String spriteUrl, Callback callback) {
        Request request = new Request.Builder().url(spriteUrl).build();
        client.newCall(request).enqueue(callback);
    }
}
