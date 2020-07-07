package com.example.meropasal.network.API;

import com.example.meropasal.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/login")
    Call<User> login(@Body User user);

}
