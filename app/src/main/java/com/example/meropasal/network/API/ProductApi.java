package com.example.meropasal.network.API;

import com.example.meropasal.models.products.Category;
import com.example.meropasal.models.products.Product;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductApi {

    @GET("product")
    Call<Product> getExclusiveProducts();

    @GET("product/order/desc")
    Call<Product> getLatestProducts();

    @GET("category/{limit}")
    Call<Category> getCategoriesWithLimit(@Path("limit") String limit);


}
