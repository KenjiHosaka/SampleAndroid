package com.example.kenjihosaka.sampleandroid.model.domain.api;

import com.example.kenjihosaka.sampleandroid.model.domain.api.ApiClient;

import retrofit2.Retrofit;

public class ApiClientTDnet {

    private static String BASE_URL = "https://webapi.yanoshin.jp/webapi/tdnet/list/";

    public static Retrofit retrofitInstance() {
        return ApiClient.retrofitInstance(BASE_URL);
    }
}


