package com.chocoroll.buyto.Retrofit;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface Retrofit {
    public static final String ROOT = "http://buytogether.dothome.co.kr";
    @POST("/login/login.php")
    public void login(@Body JsonObject info, Callback<String> callback);
    @POST("/listview/categorylist.php")
    public void getDealList(Callback<JsonArray> callback);
    
}
