package com.example.pokedex.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.example.pokedex.entities.Favourites;
import com.example.pokedex.views.DetailActivity;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {

    private List<Favourites> pokemons;
    private Context context;

    public FavouritesAdapter(List<Favourites> pokemons, Context context) {
        this.pokemons = pokemons;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favourites_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int id = pokemons.get(position).getId();
        String name = pokemons.get(position).getName();

        byte[] bytesSprite = pokemons.get(position).getSpriteImg();
        Bitmap bitmapSprite = BitmapFactory.decodeByteArray(bytesSprite, 0, bytesSprite.length);

        holder.imgSprite.setImageBitmap(bitmapSprite);
        holder.txtName.setText(capitalizaText(name));
        holder.btnSprite.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("pokemonName", name);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btnSprite;
        TextView txtName;
        ImageView imgSprite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSprite = itemView.findViewById(R.id.btnSprite);
            txtName = itemView.findViewById(R.id.txtName);
            imgSprite = itemView.findViewById(R.id.imgSprite);
        }
    }

    public String capitalizaText(String text) {
        char firstLetter = Character.toUpperCase(text.charAt(0));
        return firstLetter + text.substring(1);
    }
}
