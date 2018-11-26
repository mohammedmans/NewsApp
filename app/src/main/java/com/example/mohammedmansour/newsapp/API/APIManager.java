package com.example.mohammedmansour.newsapp.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {
    private static Retrofit mRetrofitInstance;

    private static Retrofit getmRetrofitInstance() {
        if (mRetrofitInstance == null) {
            mRetrofitInstance = new Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofitInstance;
    }
    public static APIs getAPIs(){
        return getmRetrofitInstance().create(APIs.class);
    }
}
