package com.example.meropasal.data.interactors.user;

import com.example.meropasal.models.user.ShippingAddress;
import com.example.meropasal.models.user.User;
import com.example.meropasal.network.API.UserApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.presenters.user.ShippingAddressPresenter;
import com.example.meropasal.views.ShippingAddressContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingAddressInteractor {

    private ShippingAddressContract.Presenter presenter;

    public ShippingAddressInteractor(ShippingAddressPresenter presenter){
        this.presenter = presenter;
    }

    public void addShippingAddress(ShippingAddress shippingAddress, String token){
        UserApi api = RetrofitIniti.initialize().create(UserApi.class);
        Call<User> responseCall = api.addShippingAddress(token, shippingAddress);

        responseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.onAddShippingAddress();
                    }else{
                        presenter.onFailed(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                presenter.onFailed("Something Went Wrong!");
            }
        });


    }

    public void getShippingAddress(String token){
        UserApi api = RetrofitIniti.initialize().create(UserApi.class);
        Call<User> responseCall = api.getShippingAddress(token);

        responseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.getShippingAddress(response.body().getShippingAddress());
                    }else{
                        presenter.onFailed(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                presenter.onFailed("Something Went Wrong!");
            }
        });
    }


}
