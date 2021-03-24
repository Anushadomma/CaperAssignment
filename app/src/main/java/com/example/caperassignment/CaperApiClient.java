package com.example.caperassignment;

import android.app.Application;
import android.content.Context;
import android.view.animation.PathInterpolator;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CaperApiClient {

    private static Retrofit retrofit = null;
    public static CaperApi getItem(){
        if (retrofit == null) {

             String jsonFileString = Utils.getAssetJsonData();

            OkHttpClient okHttpClient=new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request newRequest=chain.request().newBuilder().build();
                            return chain.proceed(newRequest);
                        }
                    }).build();
            retrofit=new Retrofit.Builder()
                    .baseUrl(jsonFileString)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit.create(CaperApi.class);

    }
}
