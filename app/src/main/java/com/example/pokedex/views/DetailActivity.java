package com.example.pokedex.views;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokedex.R;
import com.example.pokedex.viewModels.StartViewModel;
import com.google.gson.JsonArray;

import java.util.Locale;
@RequiresApi(api = Build.VERSION_CODES.N)
public class DetailActivity extends AppCompatActivity {
    private StartViewModel startViewModel;
    TextView name,peso,exp,altura,abilities;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String pokemonName = getIntent().getStringExtra("pokemonName");
        String apiURLpokemon = "https://pokeapi.co/api/v2/pokemon/"+pokemonName.toLowerCase();
        startViewModel = new ViewModelProvider(this).get(StartViewModel.class);
        declareComponents();
        getPokemon(apiURLpokemon);
    }

    public void getPokemon(String apiUrl) {
        startViewModel.setInitialPokemonDetailResponse(apiUrl);
        startViewModel.getCompleteResponse2().observe(this, pokemonDetail -> {
            String pokemonName = pokemonDetail.getName();
            String pokemonWeight = Integer.toString(pokemonDetail.getWeight());
            String pokemonBaseExperience = Integer.toString(pokemonDetail.getBase_experience());
            String pokemonHeight = Integer.toString(pokemonDetail.getHeight());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), pokemonDetail.getSpriteImg());
            JsonArray pokemonAbilities = pokemonDetail.getAbilities();
            name.setText(pokemonName);
            peso.setText(pokemonWeight);
            altura.setText(pokemonHeight);
            exp.setText(pokemonBaseExperience);
            abilities.setText(pokemonAbilities.toString());
            img.setImageDrawable(bitmapDrawable);
        });
    }

    public void declareComponents(){
        name = findViewById(R.id.nameTextView);
        peso = findViewById(R.id.pesoTextView);
        altura = findViewById(R.id.alturaTextView);
        abilities = findViewById(R.id.abilitiesTextView);
        exp = findViewById(R.id.expTextView);
        img = findViewById(R.id.pokemonImageView);
    }

}

