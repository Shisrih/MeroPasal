package com.example.meropasal.data.interactors.home;

import android.util.Log;

import com.example.meropasal.models.products.Category;
import com.example.meropasal.models.products.Product;
import com.example.meropasal.models.products.res.ProductRes;
import com.example.meropasal.network.API.ProductApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.presenters.home.HomePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeInteractor {

    private HomePresenter homePresenter;
    private static final String TAG = "ProductInteractor";

    public HomeInteractor(HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
    }

    public void getExclusiveProducts(){
        ProductApi api = RetrofitIniti.initialize().create(ProductApi.class);
        Call<Product> responseCall = api.getExclusiveProducts();

        responseCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                            homePresenter.getExclusiveProducts(response.body().getProducts());
                    }else{
                        homePresenter.onFailed("Failed To Get Products");
                    }

                }else{
                    homePresenter.onFailed("Something Went Wrong");

                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                t.printStackTrace();
                homePresenter.onFailed("Connection Error!");
            }
        });
    }

    public void getCategories(String limit){
        ProductApi api = RetrofitIniti.initialize().create(ProductApi.class);
        Call<Category> responseCall = api.getCategoriesWithLimit(limit);

        responseCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        homePresenter.getCategories(response.body().getCategories());
                    }else{
                        homePresenter.onFailed("Failed To Get Categories");
                    }
                }else{
                    Log.d(TAG, "onResponse: " + response.message());

                    homePresenter.onFailed("Something Went Wrong");
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getLatestProducts(){
        ProductApi api = RetrofitIniti.initialize().create(ProductApi.class);
        Call<ProductRes> responseCall = api.getLatestProducts();

        responseCall.enqueue(new Callback<ProductRes>() {
            @Override
            public void onResponse(Call<ProductRes> call, Response<ProductRes> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        homePresenter.getLatestProducts(response.body().getProducts());
                    }else{
                        homePresenter.onFailed("Failed To Get Products");
                    }

                }else{
                    homePresenter.onFailed("Something Went Wrong");

                }
            }

            @Override
            public void onFailure(Call<ProductRes> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                t.printStackTrace();
                homePresenter.onFailed("Connection Error!");
            }
        });
    }

}
