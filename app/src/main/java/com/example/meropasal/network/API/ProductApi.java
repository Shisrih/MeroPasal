package com.example.meropasal.network.API;

import com.example.meropasal.models.products.Product;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApi {

    @GET("product")
    Call<Product> getExclusiveProducts();
}
