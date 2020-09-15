package com.example.meropasal.network.API;

import com.example.meropasal.models.orders.Order;
import com.example.meropasal.models.orders.OrderRes;
import com.example.meropasal.models.user.ShippingAddress;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderApi {

    @POST("order")
    Call<Order> addOrder(@Header("authorization") String token,
                                   @Body Order order);

    @POST("order/multiple")
    Call<Order> addOrders(@Header("authorization") String token,
                         @Body List<Order> orders);

    @GET("order")
    Call<OrderRes> getOrdersByUser(@Header("authorization") String token);


    @HTTP(method = "DELETE", path = "order", hasBody = true)
    Call<Order> cancelOrders(@Header("authorization") String token,
                          @Body List<String> productid);
}
