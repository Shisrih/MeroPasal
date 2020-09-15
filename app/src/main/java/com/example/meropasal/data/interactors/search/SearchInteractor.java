package com.example.meropasal.data.interactors.search;

import android.util.Log;

import com.example.meropasal.models.products.Product;
import com.example.meropasal.models.products.res.ProductRes;
import com.example.meropasal.network.API.ProductApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.presenters.search.SearchPresenter;
import com.example.meropasal.views.SearchContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchInteractor {

    private SearchContract.Presenter presenter;
    private static final String TAG = "SearchInteractor";

    public SearchInteractor(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }


    public void getProductsBySearch(String search){
        ProductApi api = RetrofitIniti.initialize().create(ProductApi.class);
        Call<ProductRes> responseCall = api.getProductBySearch(search);

        responseCall.enqueue(new Callback<ProductRes>() {
            @Override
            public void onResponse(Call<ProductRes> call, Response<ProductRes> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.getSearchProducts(response.body().getProducts());
                    }else{
                        presenter.onFailed("Couldn't get products");
                    }
                }else{
                    presenter.onFailed("Couldn't get products");
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
