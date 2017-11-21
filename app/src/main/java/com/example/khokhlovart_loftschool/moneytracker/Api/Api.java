package com.example.khokhlovart_loftschool.moneytracker.Api;

import com.example.khokhlovart_loftschool.moneytracker.AuthRes;
import com.example.khokhlovart_loftschool.moneytracker.BalanceRes;
import com.example.khokhlovart_loftschool.moneytracker.ItemCosts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


/**
 * Created by Dom on 07.11.2017.
 */

public interface Api {
    @Headers({
            "CONTENT-TYPE: application/json",
    })
    @GET("items")
    Call<List<ItemCosts>> items(@Query("type") String type);

    @POST("items/add")
    Call<Void> items_add(@Query("price") int price, @Query("name") String name, @Query("type") String type);

    @GET("auth")
    Call<AuthRes> auth(@Query("social_user_id") String socialUserId);

    @POST("items/remove")
    Call<Void> items_remove(@Query("id") int id);

    @GET("balance")
    Call<BalanceRes> balance();

}
