package com.example.pokedex.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pokedex.R;
import com.example.pokedex.adapters.FavouritesAdapter;
import com.example.pokedex.entities.Favourites;
import com.example.pokedex.utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FavouritesAdapter favouritesAdapter;
    List<Favourites> pokemons = new ArrayList<>();
    Button btnRegresar;

    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    private Cursor pokemonCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });


        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        recyclerView = findViewById(R.id.recyclerView);
        pokemonCursor = databaseHelper.readFavourite();

        while (pokemonCursor.moveToNext()) {
            Favourites favourite = new Favourites(pokemonCursor.getInt(0), pokemonCursor.getString(1), pokemonCursor.getBlob(2));
            System.out.println(favourite.toString());
            pokemons.add(favourite);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        favouritesAdapter = new FavouritesAdapter(pokemons, this);
        recyclerView.setAdapter(favouritesAdapter);
    }
}