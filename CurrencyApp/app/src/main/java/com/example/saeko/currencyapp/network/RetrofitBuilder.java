package com.example.saeko.currencyapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by saeko on 2018-01-07.
 */

public class RetrofitBuilder {
    public static final int TIMEOUT = 30;
    private static String BASE_HTTP_URL = "http://android.coiney.com:1337";
    private static CoineyApi coineyApi = getRetrofitBuilder().create(CoineyApi.class);
    private static Retrofit retrofit;


    private static Retrofit getRetrofitBuilder() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_HTTP_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getHttpClient())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS).build();
    }

    public static CoineyApi getCoineyApi() {
        return coineyApi;
    }
}
