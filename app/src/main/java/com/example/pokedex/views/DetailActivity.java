package com.example.pokedex.views;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.widget.TextView;
import com.example.pokedex.R;
import com.example.pokedex.viewModels.StartViewModel;

import java.util.Locale;
@RequiresApi(api = Build.VERSION_CODES.N)
public class DetailActivity extends AppCompatActivity {
    private StartViewModel startViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String apiURLpokemon = "https://pokeapi.co/api/v2/pokemon/";
        String pokemonName = getIntent().getStringExtra("pokemonName");
        startViewModel = new ViewModelProvider(this).get(StartViewModel.class);
        TextView nameTextView = findViewById(R.id.nameTextView);
        nameTextView.setText(pokemonName);
        getPokemon(apiURLpokemon+pokemonName);
    }

    public void getPokemon(String apiUrl) {
        startViewModel.setInitialPokemonDetailResponse(apiUrl);
        startViewModel.getCompleteResponse().observe(this, pokemonResponse -> {
            String pokemonName = pokemonResponse.getName();
            int pokemonId = pokemonResponse.getId() - 1;

        });
    }

}

