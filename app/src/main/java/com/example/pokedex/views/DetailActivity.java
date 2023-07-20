package com.example.pokedex.views;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokedex.R;
import com.example.pokedex.entities.PokemonDetail;
import com.example.pokedex.entities.PokemonResponse;
import com.example.pokedex.utils.DatabaseHelper;
import com.example.pokedex.viewModels.StartViewModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DetailActivity extends AppCompatActivity {
    private StartViewModel startViewModel;
    TextView name, peso, exp, altura;
    TextView[] abilities = new TextView[3];
    Button btnFavourite, btnRegresar;
    ImageView img;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    Integer i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        String pokemonName = getIntent().getStringExtra("pokemonName");
        String apiURLpokemon = "https://pokeapi.co/api/v2/pokemon/" + pokemonName.toLowerCase();
        startViewModel = new ViewModelProvider(this).get(StartViewModel.class);
        declareComponents();
        getPokemon(apiURLpokemon);
    }

    public String capitalizaText(String text) {
        char firstLetter = Character.toUpperCase(text.charAt(0));
        return firstLetter + text.substring(1);
    }

    public String addComa(String number) {
        int longitud = number.length();
        StringBuilder resultado = new StringBuilder(number);

        if (longitud <= 1) {
            resultado.insert(0, "0,");
            return resultado.toString();
        } else {
            resultado.insert(longitud - 1, ",");
            return resultado.toString();
        }
    }

    public void getPokemon(String apiUrl) {
        startViewModel.setInitialPokemonDetailResponse(apiUrl);
        startViewModel.getCompleteResponse2().observe(this, pokemonDetail -> {
            String pokemonName = pokemonDetail.getName();
            String pokemonWeight = Integer.toString(pokemonDetail.getWeight());
            String pokemonBaseExperience = Integer.toString(pokemonDetail.getBase_experience());
            String pokemonHeight = Integer.toString(pokemonDetail.getHeight());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), pokemonDetail.getSpriteImg());
            name.setText(capitalizaText(pokemonName));
            peso.setText(addComa(pokemonWeight) + "kg");
            altura.setText(addComa(pokemonHeight) + "m");
            exp.setText(pokemonBaseExperience + "xp");
            JsonArray pokemonAbilities = pokemonDetail.getAbilities().getAsJsonArray();
            for (JsonElement abilityObj : pokemonAbilities) {
                JsonObject ability = abilityObj.getAsJsonObject().get("ability").getAsJsonObject();
                String abilityName = ability.get("name").getAsString();
                abilities[i].setText(capitalizaText(abilityName));
                i++;
            }

            img.setImageDrawable(bitmapDrawable);
            btnFavourite.setTag(pokemonDetail);
            defineBtnFavourite(databaseHelper.checkFavourite(pokemonDetail.getId()));
        });
    }

    public void defineBtnFavourite(boolean isFavourite) {
        int alpha = (isFavourite) ? 256 : 128;
        btnFavourite.getBackground().setAlpha(alpha);

    }

    public void declareComponents() {
        name = findViewById(R.id.nameTextView);
        peso = findViewById(R.id.pesoTextView);
        altura = findViewById(R.id.alturaTextView);
        abilities[0] = findViewById(R.id.abilitiesTextView);
        abilities[1] = findViewById(R.id.abilitiesTextView2);
        abilities[2] = findViewById(R.id.abilitiesTextView3);

        exp = findViewById(R.id.expTextView);
        img = findViewById(R.id.pokemonImageView);

        btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });

        btnFavourite = findViewById(R.id.btnFavourite);
        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PokemonDetail pokemonDetail = (PokemonDetail) btnFavourite.getTag();
                boolean isFavourite = databaseHelper.checkFavourite(pokemonDetail.getId());
                if (isFavourite) {
                    databaseHelper.deleteData(pokemonDetail.getId());
                } else {
                    databaseHelper.createFavourite(pokemonDetail.getId(), pokemonDetail.getName(), pokemonDetail.getSpriteImg());
                }
                defineBtnFavourite(!isFavourite);
            }
        });
    }
}

