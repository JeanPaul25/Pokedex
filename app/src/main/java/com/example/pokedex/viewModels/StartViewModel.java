package com.example.pokedex.viewModels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokedex.entities.PokemonResponse;
import com.example.pokedex.models.PokemonRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class StartViewModel extends ViewModel {
    private PokemonRepository pokemonRepository = new PokemonRepository();
    private MutableLiveData<PokemonResponse> completeResponseLiveData = new MutableLiveData<>();

    public LiveData<PokemonResponse> getCompleteResponse() {
        return completeResponseLiveData;
    }

    public void setInitialPokemonResponse(String apiUrl) {
        pokemonRepository.repoBasicResponse(apiUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.getStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                JsonElement jsonElement = JsonParser.parseString(responseBody);
                JsonArray jsonResult = jsonElement.getAsJsonObject().getAsJsonArray("results");

                for (JsonElement result : jsonResult) {
                    String pokemonName = result.getAsJsonObject().get("name").getAsString();
                    String pokemonUrl = result.getAsJsonObject().get("url").getAsString();
                    PokemonResponse pokemonResponse = new PokemonResponse(pokemonName, pokemonUrl, 0, null, null);
                    setPartialPokemonResponse(pokemonResponse);
                }
            }
        });
    }

    public void setPartialPokemonResponse(PokemonResponse pokemonResponse) {
        pokemonRepository.repoPartialStartResponse(pokemonResponse.getPokemonUrl(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.getStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                JsonElement jsonElement = JsonParser.parseString(responseBody);
                int id = jsonElement.getAsJsonObject().get("id").getAsInt();
                String spriteUrl = jsonElement.getAsJsonObject().get("sprites").getAsJsonObject().get("front_default").getAsString();
                pokemonResponse.setId(id);
                pokemonResponse.setSpriteUrl(spriteUrl);
                setCompletePokemonResponse(pokemonResponse);
            }
        });
    }

    public void setCompletePokemonResponse(PokemonResponse pokemonResponse) {
        pokemonRepository.repoCompleteStartResponse(pokemonResponse.getSpriteUrl(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.getStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                pokemonResponse.setSpriteImg(bitmap);
                completeResponseLiveData.postValue(pokemonResponse);
            }
        });
    }
}
