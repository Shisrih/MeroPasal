package com.example.meropasal.data.interactors.order;

import com.example.meropasal.models.orders.Order;
import com.example.meropasal.network.API.OrderApi;
import com.example.meropasal.network.RetrofitIniti;
import com.example.meropasal.views.CartOrdersContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartOrdersInteractor {

    private CartOrdersContract.Presenter presenter;

    public CartOrdersInteractor(CartOrdersContract.Presenter presenter) {
        this.presenter = presenter;
    }


    public void addOrders(List<Order> orders, String token){

        OrderApi api = RetrofitIniti.initialize().create(OrderApi.class);
        Call<Order> responseCall = api.addOrders(token, orders);

        responseCall.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess()){
                        presenter.onSuccess();
                    }else{
                        presenter.onFailed("Couldn't place orders");
                    }
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                presenter.onFailed("Something Went Wrong!");
            }
        });
    }

}
