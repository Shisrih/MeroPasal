package com.example.meropasal.data.interactors.product;

import com.example.meropasal.models.products.res.ProductRes;
import com.example.meropasal.network.API.ProductApi;
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
                    presenter.onFailed("");
                }
            }

            @Override
            public void onFailure(Call<ProductRes> call, Throwable t) {

            }
        });


    }

}
