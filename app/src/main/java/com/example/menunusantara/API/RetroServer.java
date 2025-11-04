package com.example.menunusantara.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    // BASE URL telah diperbaiki menjadi '/menunusantara/' sesuai konteks Anda
    private static final String baseURL = "http://192.168.43.70/menunusantara/";

    private static Retrofit retrofit;

    // Metode publik untuk mendapatkan instance Retrofit
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
