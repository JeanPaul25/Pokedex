package com.example.pokedex.utils;

import com.example.pokedex.entities.BasicResponse;
import com.google.gson.Gson;

public class JsonParse {

    public BasicResponse jsonParseBasicResponse(String responseString) {
        Gson gson = new Gson();
        return gson.fromJson(responseString, BasicResponse.class);
    }
}
