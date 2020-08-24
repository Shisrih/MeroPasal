package com.example.meropasal.network.API;

import com.example.meropasal.models.orders.Order;
import com.example.meropasal.models.user.ShippingAddress;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderApi {

    @POST("order")
    Call<Order> addOrder(@Header("authorization") String token,
                                   @Body Order order);

    @POST("order/multiple")
    Call<Order> addOrders(@Header("authorization") String token,
                         @Body List<Order> orders);
}
