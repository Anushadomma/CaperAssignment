package com.example.caperassignment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CaperApi {


    @GET("/goods/{id}")
    Call<CaperData> getItem(@Path("id") String code);
}
