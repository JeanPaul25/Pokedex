package com.example.pokedex.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pokedex.R;
import com.example.pokedex.entities.PokemonResponse;
import com.example.pokedex.utils.DatabaseHelper;
import com.example.pokedex.viewModels.StartViewModel;

import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class StartActivity extends AppCompatActivity {

    TextView[] texts = new TextView[12];
    Button[] buttons = new Button[12];
    Button btnFavourites;
    TextView txtpag;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    private StartViewModel startViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        String initialApiURl = "https://pokeapi.co/api/v2/pokemon?limit=12&offset=0";
        declareComponents();

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        startViewModel = new ViewModelProvider(this).get(StartViewModel.class);

        //databaseHelper.onUpgrade(database, 1, 1);
        paginationButtons();
        getPokemons(initialApiURl);
    }

    public void addFavourite(DatabaseHelper database, Object pokemon) {
        System.out.println(pokemon);
        PokemonResponse pokemonResponse = (PokemonResponse) pokemon;
        database.createFavourite(pokemonResponse.getId(), pokemonResponse.getName(), pokemonResponse.getSpriteImg());
    }

    public void getPokemons(String apiUrl) {
        startViewModel.setInitialPokemonResponse(apiUrl);

        startViewModel.getCompleteResponse().observe(this, pokemonResponse -> {
            String pokemonName = pokemonResponse.getName();
            int pokemonId = pokemonResponse.getId() - 1;
            int tempId = pokemonId, pagination = 0;
            while (tempId >= 12) {
                tempId -= 12;
                pagination++;
            }
            txtpag.setText(String.valueOf(pagination + 1));
            texts[pokemonId - (12 * pagination)].setText(pokemonName.substring(0, 1).toUpperCase(Locale.ROOT) + pokemonName.substring(1));
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), pokemonResponse.getSpriteImg());
            buttons[pokemonId - (12 * pagination)].setBackground(bitmapDrawable);
            buttons[pokemonId - (12 * pagination)].setTag(pokemonResponse);
        });
    }

    public void paginationButtons() {
        Button btnPrev = findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPokemons(startViewModel.prevApiUrl);
            }
        });

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPokemons(startViewModel.nextApiUrl);
            }
        });
    }

    private void openDetailActivity(String name) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("pokemonName", name);
        startActivity(intent);
    }

    public void declareComponents() {
        buttons[0] = findViewById(R.id.btn1);
        buttons[1] = findViewById(R.id.btn2);
        buttons[2] = findViewById(R.id.btn3);
        buttons[3] = findViewById(R.id.btn4);
        buttons[4] = findViewById(R.id.btn5);
        buttons[5] = findViewById(R.id.btn6);
        buttons[6] = findViewById(R.id.btn7);
        buttons[7] = findViewById(R.id.btn8);
        buttons[8] = findViewById(R.id.btn9);
        buttons[9] = findViewById(R.id.btn10);
        buttons[10] = findViewById(R.id.btn11);
        buttons[11] = findViewById(R.id.btn12);

        btnFavourites = findViewById(R.id.btnFavourites);
        btnFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, FavouritesActivity.class);
                startActivity(intent);
            }
        });

        texts[0] = findViewById(R.id.txt1);
        texts[1] = findViewById(R.id.txt2);
        texts[2] = findViewById(R.id.txt3);
        texts[3] = findViewById(R.id.txt4);
        texts[4] = findViewById(R.id.txt5);
        texts[5] = findViewById(R.id.txt6);
        texts[6] = findViewById(R.id.txt7);
        texts[7] = findViewById(R.id.txt8);
        texts[8] = findViewById(R.id.txt9);
        texts[9] = findViewById(R.id.txt10);
        texts[10] = findViewById(R.id.txt11);
        texts[11] = findViewById(R.id.txt12);

        txtpag = findViewById(R.id.txtPag);

        for (int i = 0; i < 12; i++) {
            final int index = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDetailActivity(texts[index].getText().toString());
                }
            });
        }

    }

}