package com.example.pembayaranspp.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://172.16.101.8/ukk_lintang2021/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //TODO 2 membuat object service api dengan retrovit
    public ApiInterface service = retrofit.create(ApiInterface.class);

}
