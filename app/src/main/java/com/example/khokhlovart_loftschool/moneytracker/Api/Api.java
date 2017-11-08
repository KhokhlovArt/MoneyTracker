package com.example.khokhlovart_loftschool.moneytracker.Api;

import com.example.khokhlovart_loftschool.moneytracker.ItemCosts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


/**
 * Created by Dom on 07.11.2017.
 */

public interface Api {
    @Headers({
            "CONTENT-TYPE: application/json",
    })
    @GET("items")
    Call<List<ItemCosts>> items(@Query("type") int type);

    @Headers({
            "CONTENT-TYPE: application/json",
    })
    @GET("add")
    Call<ItemCosts> add();
}
