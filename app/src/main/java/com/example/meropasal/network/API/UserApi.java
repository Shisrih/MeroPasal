package com.example.meropasal.network.API;
import com.example.meropasal.models.user.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserApi {


    @GET("user")
    Call<User> getUser(@Header("authorization") String token);

}
