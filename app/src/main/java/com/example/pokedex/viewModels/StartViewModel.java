package com.example.pokedex.viewModels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokedex.entities.PokemonResponse;
import com.example.pokedex.models.PokemonRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class StartViewModel extends ViewModel {
    private PokemonRepository pokemonRepository = new PokemonRepository();

    private MutableLiveData<PokemonResponse> completeResponseLiveData = new MutableLiveData<>();
    public LiveData<PokemonResponse> getCompleteResponse() {
        return completeResponseLiveData;
    }

    public String prevApiUrl, nextApiUrl;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setInitialPokemonResponse(String apiUrl) {
        final CompletableFuture<Void> future = new CompletableFuture<>();
        pokemonRepository.repoBasicResponse(apiUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String responseBody = response.body().string();
                    JsonElement jsonElement = JsonParser.parseString(responseBody);
                    JsonArray jsonResult = jsonElement.getAsJsonObject().getAsJsonArray("results");
                    nextApiUrl = jsonElement.getAsJsonObject().get("next").getAsString();
                    prevApiUrl = jsonElement.getAsJsonObject().get("previous").isJsonNull() ? "" : jsonElement.getAsJsonObject().get("previous").getAsString();

                    List<CompletableFuture<Void>> futures = new ArrayList<>();
                    for (JsonElement result : jsonResult) {
                        String pokemonName = result.getAsJsonObject().get("name").getAsString();
                        String pokemonUrl = result.getAsJsonObject().get("url").getAsString();
                        PokemonResponse pokemonResponse = new PokemonResponse(pokemonName, pokemonUrl, null, null, null);
                        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> setPartialPokemonResponse(pokemonResponse));
                        futures.add(future);
                    }
                    CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
                    allFutures.thenAccept((Void) -> future.complete(null))
                            .exceptionally(ex -> {
                                future.completeExceptionally(ex);
                                return null;
                            });
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            }
        });

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
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
                Integer id = jsonElement.getAsJsonObject().get("id").getAsInt();
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
