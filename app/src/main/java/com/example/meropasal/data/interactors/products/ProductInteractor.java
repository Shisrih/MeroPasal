package com.example.meropasal.data.interactors.products;

import android.util.Log;

import com.example.meropasal.models.products.Product;
import com.example.meropasal.network.API.ProductApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.presenters.products.ProductPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInteractor {

    private ProductPresenter productPresenter;
    private static final String TAG = "ProductInteractor";

    public ProductInteractor(ProductPresenter productPresenter) {
        this.productPresenter = productPresenter;
    }

    public void getExclusiveProducts(){
        ProductApi api = RetrofitIniti.initialize().create(ProductApi.class);
        Call<Product> responseCall = api.getExclusiveProducts();

        responseCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                            productPresenter.getExclusiveProducts(response.body().getProducts());
                    }else{
                        productPresenter.onFailed("Failed To Get Products");
                    }

                }else{
                    productPresenter.onFailed("Something Went Wrong");

                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                productPresenter.onFailed("Connection Error!");
            }
        });
    }

}
