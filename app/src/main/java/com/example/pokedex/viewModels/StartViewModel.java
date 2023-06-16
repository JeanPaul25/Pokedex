package com.example.pokedex.viewModels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokedex.entities.BasicResponse;
import com.example.pokedex.entities.Pokemon;
import com.example.pokedex.entities.StartResponse;
import com.example.pokedex.models.PokemonRepository;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class StartViewModel extends ViewModel {

    private MutableLiveData<BasicResponse> basicResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<StartResponse> startResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<StartResponse> completeResponseLiveData = new MutableLiveData<>();

    private MutableLiveData<Pair<Integer, Bitmap>> spriteImageLiveData = new MutableLiveData<>();

    private PokemonRepository pokemonRepository = new PokemonRepository();
    private Gson gson = new Gson();

    public LiveData<BasicResponse> getBasicResponse() {
        return basicResponseLiveData;
    }

    public LiveData<StartResponse> getStartResponse() {
        return startResponseLiveData;
    }

    public LiveData<StartResponse> getCompleteResponse() {
        return completeResponseLiveData;
    }

    public void loadBasicResponse(String apiUrl) {
        pokemonRepository.getBasicResponse(apiUrl, new Callback() {
            BasicResponse basicResponse = new BasicResponse();

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.getStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                basicResponse = gson.fromJson(response.body().string(), BasicResponse.class);

                basicResponseLiveData.postValue(basicResponse);
            }
        });
    }

    StartResponse partialResponse;

    public void loadPartialResponse(Pokemon[] pokemons) {
        for (Pokemon pokemon : pokemons) {
            pokemonRepository.getPartialResponse(pokemon.getUrl(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.getStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseBody = response.body().string();
                    JsonElement jsonElement = JsonParser.parseString(responseBody);
                    String name = jsonElement.getAsJsonObject().get("name").getAsString();
                    int id = jsonElement.getAsJsonObject().get("id").getAsInt();
                    String spriteUrl = jsonElement.getAsJsonObject().get("sprites").getAsJsonObject().get("front_default").getAsString();

                    partialResponse = new StartResponse(name, id, spriteUrl, null);

                    startResponseLiveData.postValue(partialResponse);
                }
            });
        }
    }

    StartResponse completeResponse;

    public void loadCompleteResponse(StartResponse startResponse) {
        pokemonRepository.getCompleteResponse(startResponse, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.getStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                completeResponse = new StartResponse(startResponse.getName(), startResponse.getId(), startResponse.getSpriteUrl(), bitmap);
                completeResponseLiveData.postValue(completeResponse);
            }
        });
    }
}
