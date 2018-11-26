package com.example.mohammedmansour.newsapp.API;


import com.example.mohammedmansour.newsapp.API.Responses.Everything.EverythingResponse;
import com.example.mohammedmansour.newsapp.API.Responses.Source.SourceResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIs {

    @GET("sources")
    Call <SourceResponse> getSourceName(@Query("apiKey")String apiKey
            ,@Query("language") String lang);

    @GET("everything")
    Call<EverythingResponse> getEverythingRespone(@Query("apiKey") String apiKey
            ,@Query("sources")String sources);
}
