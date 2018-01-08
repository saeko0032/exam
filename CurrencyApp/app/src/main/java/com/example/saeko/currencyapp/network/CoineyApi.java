package com.example.saeko.currencyapp.network;

import com.example.saeko.currencyapp.network.response.MyCurrencyResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by saeko on 2018-01-07.
 */

public interface CoineyApi {
    @GET("/exchange_rates")
    Call<MyCurrencyResponse> getMyCurrencies();
}
