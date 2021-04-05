package com.cos.daangnapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface retrofitURL {
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://172.30.1.22:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
