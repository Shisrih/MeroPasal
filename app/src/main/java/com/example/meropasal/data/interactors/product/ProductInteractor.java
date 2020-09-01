package com.example.meropasal.data.interactors.product;

import android.util.Log;

import com.example.meropasal.models.review.Rating;
import com.example.meropasal.models.products.res.ProductRes;
import com.example.meropasal.network.API.ProductApi;
import com.example.meropasal.network.API.RatingApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.presenters.product.ProductPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInteractor  {

    private ProductPresenter presenter;
    private static final String TAG = "ProductInteractor";

    public ProductInteractor(ProductPresenter presenter){
        this.presenter = presenter;
    }


    public void getProductById(String id){

        ProductApi api = RetrofitIniti.initialize().create(ProductApi.class);
        Call<ProductRes> responseCall = api.getProductById(id);

        responseCall.enqueue(new Callback<ProductRes>() {
            @Override
            public void onResponse(Call<ProductRes> call, Response<ProductRes> response) {
                if(response.body().isSuccess()){
                    presenter.onSuccess(response.body().getProduct(), response.body().getAvgRatings());

                }else{
                    presenter.onFailed("Failed to get product");
                }
            }

            @Override
            public void onFailure(Call<ProductRes> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                t.printStackTrace();
                presenter.onFailed("Connection Error!");
            }
        });


    }

    public void getReviews(String productid){
        RatingApi api = RetrofitIniti.initialize().create(RatingApi.class);
        Call<Rating> responseCall = api.getRatings(productid);

        responseCall.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.getProductReviews(response.body().getReviews());
                    }else{
                        Log.d(TAG, "onResponse: " + response.body().getMessage());
                        presenter.onFailed("Couldn't get Reviews");
                    }
                }else{
                    Log.d(TAG, "onResponse: " + response.message());
                    presenter.onFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                presenter.onFailed("Couldn't get Reviews");
            }
        });
    }


    public void getSimilarProducts(String brand, String id){

        ProductApi api = RetrofitIniti.initialize().create(ProductApi.class);
        Call<ProductRes> responseCall = api.getProductsByBrand(brand, id);

        responseCall.enqueue(new Callback<ProductRes>() {
            @Override
            public void onResponse(Call<ProductRes> call, Response<ProductRes> response) {
                if(response.body().isSuccess()){
                    presenter.getSimilarProducts(response.body().getSimilarProducts());
                    Log.d(TAG, "onFailure: " + response.body().isSuccess());
                }else{
                    presenter.onFailed("Failed to get similar products");
                }
            }

            @Override
            public void onFailure(Call<ProductRes> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                t.printStackTrace();
                presenter.onFailed("Connection Error!");
            }
        });


    }

}
