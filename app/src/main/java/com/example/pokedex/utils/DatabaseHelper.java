package com.example.pokedex.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.pokedex.entities.PokemonResponse;
import com.example.pokedex.views.StartActivity;

import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PokemonDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "favourites";
    private static final String COL1 = "id";
    private static final String COL2 = "name";
    private static final String COL3 = "spriteImg";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + COL1 + " Integer PRIMARY KEY," + COL2 + " Text," + COL3 + " BLOB) ";
    private static final String DROP_TABLE = "drop table IF EXISTS " + TABLE_NAME;

    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean createFavourite(int id, String name, Bitmap spriteImg) {
        Cursor cursor = readFavourite();
        boolean isCreated = false;
        while (cursor.moveToNext()) {
            if (cursor.getInt(0) == id) {
                isCreated = true;
                break;
            }
        }
        if (isCreated) {
            Toast.makeText(context, "El pokemon ya ha sido agregado", Toast.LENGTH_SHORT).show();
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            spriteImg.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] spriteBytes = byteArrayOutputStream.toByteArray();

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL1, id);
            contentValues.put(COL2, name);
            contentValues.put(COL3, spriteBytes);
            Long result = db.insert(TABLE_NAME, null, contentValues);
            Toast.makeText(context, "El pokemon ha sido agregado a favoritos", Toast.LENGTH_SHORT).show();
            return (result == -1) ? false : true;
        }
        return false;
    }

    public Cursor readFavourite() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);
        return result;
    }

    public boolean deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowDeleted = db.delete(TABLE_NAME, COL1, new String[]{String.valueOf(id)});
        return rowDeleted > 0;
    }
}

