package com.example.pokedex.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pokedex.R;
import com.example.pokedex.entities.BasicResponse;
import com.example.pokedex.entities.Pokemon;
import com.example.pokedex.viewModels.StartViewModel;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class StartActivity extends AppCompatActivity {

//    OkHttpClient client = new OkHttpClient();


    TextView[] texts = new TextView[15];
    Button[] buttons = new Button[15];
    private StartViewModel startViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        String apiUrl = "https://pokeapi.co/api/v2/pokemon?limit=12&offset=0";
        String apiUrlNext = "https://pokeapi.co/api/v2/pokemon?offset=12&limit=12";
        declareComponents();

        startViewModel = new ViewModelProvider(this).get(StartViewModel.class);

        Button btnRetry = findViewById(R.id.btnRetry);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getPokemons(apiUrlNext, 0);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });

        getPokemons(apiUrl, 0);
    }

    public void getPokemons(String apiUrl, int pagination) {
        startViewModel.loadBasicResponse(apiUrl);

        startViewModel.getBasicResponse().observe(this, pokemonResponse -> {
            Pokemon[] pokemon = pokemonResponse.getResults();
            startViewModel.loadPartialResponse(pokemon);
        });

        startViewModel.getStartResponse().observe(this, spriteUrlResponse -> {
            startViewModel.loadCompleteResponse(spriteUrlResponse);
        });

        startViewModel.getCompleteResponse().observe(this, startCompleteResponse -> {
            String pokemonName = startCompleteResponse.getName();
            int pokemonId = startCompleteResponse.getId() - 1;
            texts[pokemonId - (12 * pagination)].setText(pokemonName.substring(0, 1).toUpperCase(Locale.ROOT) + pokemonName.substring(1));
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), startCompleteResponse.getSpriteImg());
            buttons[pokemonId - (12 * pagination)].setBackground(bitmapDrawable);
        });
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
    }
}