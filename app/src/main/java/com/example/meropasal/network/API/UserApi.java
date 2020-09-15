package com.example.meropasal.network.API;
import com.example.meropasal.models.orders.Order;
import com.example.meropasal.models.user.ShippingAddress;
import com.example.meropasal.models.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApi {


    @GET("user")
    Call<User> getUser(@Header("authorization") String token);

    @GET("user/shippingAddress")
    Call<User> getShippingAddress(@Header("authorization") String token);

    @POST("user/shippingAddress")
    Call<User> addShippingAddress(@Header("authorization") String token,
                                  @Body ShippingAddress shippingAddress);


    @HTTP(method = "PUT", path = "user", hasBody = true)
    Call<User> updateUserDetails(@Header("authorization") String token,
                             @Body User user);

}
